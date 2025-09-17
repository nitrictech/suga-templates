# Suga Python FastAPI Example Project

A FastAPI web service template using the Suga framework for cloud resource access.

## Prerequisites

- Python 3.10+
- [uv](https://github.com/astral-sh/uv)
- [Suga CLI](https://docs.addsuga.com/installation)

## Setup

### Build the Suga SDK

```bash
make generate
```

### Install dependencies

```bash
uv venv
source .venv/bin/activate
uv sync
```

## Running the Application (Development)

```bash
suga dev
```

This will start your FastAPI application using the script defined in `suga.yaml`:

```yaml
script: uv run uvicorn main:app --host 0.0.0.0 --port $PORT
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
