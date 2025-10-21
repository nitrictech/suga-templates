package com.example.helloworldapi;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SimpleHttpServer {

    private static final String LOG_FILE = "user_names.txt";

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        // Create API endpoints
        server.createContext("/api/hello", new HelloHandler());
        server.createContext("/api/hello/name", new HelloNameHandler());
        server.createContext("/api/", new RootHandler());
        server.createContext("/", new WelcomeHandler());
        
        server.setExecutor(null); // creates a default executor
        server.start();
        
        System.out.println("Hello World API Server started on port 8080");
        System.out.println("Available endpoints:");
        System.out.println("  GET http://localhost:8080/api/hello");
        System.out.println("  GET http://localhost:8080/api/hello/name?name=YourName");
        System.out.println("  GET http://localhost:8080/api/");
        System.out.println("  GET http://localhost:8080/");
    }

    static class HelloHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = "Hello, World!";
                sendResponse(exchange, response);
            } else {
                sendResponse(exchange, "Method not allowed", 405);
            }
        }
    }

    static class HelloNameHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                URI requestURI = exchange.getRequestURI();
                String query = requestURI.getQuery();
                String name = "World";
                
                if (query != null) {
                    Map<String, String> queryParams = parseQuery(query);
                    name = queryParams.getOrDefault("name", "World");
                }
                
                // Log the name to file if it's not the default "World"
                if (!"World".equals(name)) {
                    logNameToFile(name);
                }
                
                String response = "Hello, " + name + "!";
                sendResponse(exchange, response);
            } else {
                sendResponse(exchange, "Method not allowed", 405);
            }
        }
    }

    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = "Welcome to the Hello World API! Try /api/hello or /api/hello/name?name=YourName";
                sendResponse(exchange, response);
            } else {
                sendResponse(exchange, "Method not allowed", 405);
            }
        }
    }

    static class WelcomeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = "Hello World API Server is running! Visit /api/ for API endpoints.";
                sendResponse(exchange, response);
            } else {
                sendResponse(exchange, "Method not allowed", 405);
            }
        }
    }

    private static void sendResponse(HttpExchange exchange, String response) throws IOException {
        sendResponse(exchange, response, 200);
    }

    private static void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static void logNameToFile(String name) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write(String.format("[%s] User name: %s%n", timestamp, name));
            System.out.println("Logged name to file: " + name);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    private static Map<String, String> parseQuery(String query) {
        Map<String, String> result = new HashMap<>();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    result.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return result;
    }
}