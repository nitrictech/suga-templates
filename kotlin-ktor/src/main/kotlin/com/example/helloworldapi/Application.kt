package com.example.helloworldapi

import com.addsuga.SugaClient
import com.addsuga.Bucket
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import io.ktor.server.netty.EngineMain

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureRouting()
}

fun Application.configureRouting() {
    val sugaClient = SugaClient()
    val imageBucket = sugaClient.createBucket("image")
    val logKey = "user_names.txt"

    routing {
        route("/api") {
            get("/hello") {
                call.respondText("Hello, World!")
            }

            get("/hello/name") {
                val name = call.request.queryParameters["name"] ?: "World"
                
                // Log the name to S3 bucket if it's not the default "World"
                if (name != "World") {
                    logNameToBucket(imageBucket, logKey, name)
                }
                
                call.respondText("Hello, $name!")
            }

            get("/") {
                call.respondText("Welcome to the Hello World API! Try /api/hello or /api/hello/name?name=YourName")
            }

            get("/logs") {
                try {
                    val logsData = imageBucket.read(logKey)
                    val logs = String(logsData)
                    call.respondText("User logs from S3 bucket:\n$logs")
                } catch (e: Exception) {
                    println("Error reading from S3 bucket: ${e.message}")
                    call.respondText("Error reading logs from S3 bucket: ${e.message}", status = HttpStatusCode.InternalServerError)
                }
            }
        }
    }
}

private fun logNameToBucket(imageBucket: Bucket, logKey: String, name: String) {
    try {
        val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        val logEntry = "[$timestamp] User name: $name\n"
        
        // Read existing logs and append new entry
        val existingLogs = try {
            val existingData = imageBucket.read(logKey)
            String(existingData)
        } catch (e: Exception) {
            // If file doesn't exist or can't be read, start with empty string
            println("Starting new log file in S3 bucket")
            ""
        }
        
        val updatedLogs = existingLogs + logEntry
        imageBucket.write(logKey, updatedLogs.toByteArray())
        
        println("Logged name to S3 bucket: $name")
    } catch (e: Exception) {
        println("Error writing to S3 bucket: ${e.message}")
    }
}