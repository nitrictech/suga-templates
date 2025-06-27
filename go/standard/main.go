package main

import (
	"example/suga"
	"fmt"
	"log"
	"net/http"
	"os"
)

func main() {
	router := http.NewServeMux()

	app, err := suga.NewClient()
	if err != nil {
		log.Fatalf("Failed to create suga client: %v", err)
	}

	// setup a basic http server
	router.HandleFunc("GET /hello/{name}", func(w http.ResponseWriter, r *http.Request) {
		name := r.PathValue("name")

		// Use the suga client to access cloud resources
		app.Files.Write("example.txt", []byte("Hello, "+name+"!"))

		w.WriteHeader(http.StatusOK)
		w.Write([]byte("Hello, " + name + "!"))
	})

	port := os.Getenv("PORT")

	server := &http.Server{
		Addr:    ":" + port,
		Handler: router,
	}

	fmt.Printf("Server started on port %s, however use the entrypoint port to connect to the server\n", port)

	server.ListenAndServe()
}
