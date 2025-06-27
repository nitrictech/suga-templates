install: generate
	go mod tidy
	go mod download

generate:
	suga generate --go --go-out ./suga --go-package-name suga

version:
	suga version

run:
	suga dev

build:
	suga build
