# Suga TypeScript Example Project

A TypeScript (Express) web service template using the Suga framework for cloud resource access.

## Prerequisites

- Node.js 18+
- npm
- [Suga CLI](https://docs.addsuga.com/cli/installation)

## Setup

### Build the Suga SDK

```bash
make generate
```

### Install dependencies

```bash
npm install
```

## Running the Application (Development)

```bash
suga dev
```

This will start your Express application using the script defined in `suga.yaml`:

```yaml
script: npm run dev
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
