package com.example.helloworldapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.addsuga.SugaClient;
import com.addsuga.Bucket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
public class HelloWorldController {

    private final SugaClient sugaClient;
    private final Bucket imageBucket;
    private static final String LOG_KEY = "user_names.txt";

    public HelloWorldController() {
        this.sugaClient = new SugaClient();
        this.imageBucket = this.sugaClient.createBucket("image");
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/hello/name")
    public String helloName(@RequestParam(value = "name", defaultValue = "World") String name) {
        // Log the name to S3 bucket if it's not the default "World"
        if (!"World".equals(name)) {
            logNameToBucket(name);
        }
        return String.format("Hello, %s!", name);
    }

    @GetMapping("/")
    public String root() {
        return "Welcome to the Hello World API! Try /api/hello or /api/hello/name?name=YourName";
    }

    @GetMapping("/logs")
    public String getLogs() {
        try {
            byte[] logsData = imageBucket.read(LOG_KEY);
            String logs = new String(logsData);
            return "User logs from S3 bucket:\n" + logs;
        } catch (Exception e) {
            System.err.println("Error reading from S3 bucket: " + e.getMessage());
            return "Error reading logs from S3 bucket: " + e.getMessage();
        }
    }

    private void logNameToBucket(String name) {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String logEntry = String.format("[%s] User name: %s%n", timestamp, name);
            
            // Read existing logs and append new entry
            String existingLogs = "";
            try {
                byte[] existingData = imageBucket.read(LOG_KEY);
                existingLogs = new String(existingData);
            } catch (Exception e) {
                // If file doesn't exist or can't be read, start with empty string
                System.out.println("Starting new log file in S3 bucket");
            }
            
            String updatedLogs = existingLogs + logEntry;
            imageBucket.write(LOG_KEY, updatedLogs.getBytes());
            
            System.out.println("Logged name to S3 bucket: " + name);
        } catch (Exception e) {
            System.err.println("Error writing to S3 bucket: " + e.getMessage());
        }
    }
}