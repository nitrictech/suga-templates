# Suga Django Example Project

A Django web service template using the Suga framework for cloud resource access.

## Prerequisites

- Python 3.10+
- pip
- [Suga CLI](https://suga.io/docs/installation)

## Setup

### Build the Suga SDK
```bash
make generate
```

### Install dependencies
```bash
python3 -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
```

## Running the Application (Development)

```bash
suga dev
```

This will start your Django application using the script defined in `suga.yaml`:

```yaml
script: python manage.py runserver 0.0.0.0:$PORT
```

## Example Usage

Write an object:

```bash
curl -X POST http://localhost:4000/write/test-object.txt/ \
  -H "Content-Type: text/plain" \
  --data "Hello from curl!"
```

Read an object:

```bash
curl http://localhost:4000/read/test-object.txt/
```
