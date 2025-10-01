# Suga Go Example Project

A Go web service template using the Suga framework for cloud resource access.

## Prerequisites

- Go 1.20+
- [Suga CLI](https://docs.addsuga.com/installation)

## Setup

### Build the Suga SDK

```bash
make generate
```

### Install dependencies

```bash
go mod tidy
go mod download
```

## Running the Application (Development)

```bash
suga dev
```

This will start your Go application using the script defined in `suga.yaml`:

```yaml
script: go run main.go
```

## Example Usage

Write an object:

```bash
curl -X POST http://localhost:4000/write/test-object.txt \
  -H "Content-Type: text/plain" \
  --data "Hello from curl!"
```

Read an object:

```bash
curl http://localhost:4000/read/test-object.txt
```
