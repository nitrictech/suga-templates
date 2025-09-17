# Suga TypeScript Example Project

A TypeScript (Express) web service template using the Suga framework for cloud resource access.

## Prerequisites

- Node.js 18+
- Any package manager (e.g., npm, yarn, bun)
- [Suga CLI](https://docs.addsuga.com/installation)

## Setup

### Build the Suga SDK

```bash
npm run generate
```

### Install dependencies

```bash
npm install
```

**Using alternative package managers:**
- For yarn/pnpm: Delete `package-lock.json` first, then run `yarn install` or `pnpm install`
- For bun: Run `bun install` (auto-migrates from npm). You may want to delete `package-lock.json` afterwards to avoid confusion

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
