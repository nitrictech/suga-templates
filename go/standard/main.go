package main

import (
	"example/suga"
	"fmt"
	"io"
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
	router.HandleFunc("GET /read/{name}", func(w http.ResponseWriter, r *http.Request) {
		name := r.PathValue("name")

		contents, err := app.Image.Read(name)
		if err != nil {
			http.Error(w, fmt.Sprintf(`{"detail": "%s"}`, err.Error()), http.StatusInternalServerError)
			return
		}

		w.Header().Set("Content-Type", "text/plain")
		w.WriteHeader(http.StatusOK)
		w.Write(contents)
	})

	router.HandleFunc("POST /write/{name}", func(w http.ResponseWriter, r *http.Request) {
		name := r.PathValue("name")

		body, err := io.ReadAll(r.Body)
		if err != nil {
			http.Error(w, fmt.Sprintf(`{"detail": "%s"}`, err.Error()), http.StatusInternalServerError)
			return
		}
		defer r.Body.Close()

		err = app.Image.Write(name, body)
		if err != nil {
			http.Error(w, fmt.Sprintf(`{"detail": "%s"}`, err.Error()), http.StatusInternalServerError)
			return
		}

		w.Header().Set("Content-Type", "text/plain")
		w.WriteHeader(http.StatusOK)
		w.Write([]byte(fmt.Sprintf("File '%s' written to bucket.", name)))
	})

	port := os.Getenv("PORT")

	server := &http.Server{
		Addr:    ":" + port,
		Handler: router,
	}

	fmt.Printf("Server started on port %s, however use the entrypoint port to connect to the server\n", port)

	server.ListenAndServe()
}
