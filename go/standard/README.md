# Suga Go Example Project

A Go web service template using the Suga framework for cloud resource access.

## Prerequisites

- Go 1.20+
- [Suga CLI](https://docs.addsuga.com/cli/installation)

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

Write and read a greeting:

```bash
curl http://localhost:4000/hello/world
```
