"""FastAPI application with Suga client for image storage operations."""

from fastapi import FastAPI, HTTPException, Request
from fastapi.responses import PlainTextResponse

from suga_gen.client import SugaClient

app = FastAPI()
suga = SugaClient()


@app.get("/")
async def hello():
    """Return a simple hello world response."""
    return PlainTextResponse("Hello, World!")


@app.get("/read/{name}")
async def read_from_bucket(name: str):
    """Read image content from bucket by name."""
    try:
        contents = suga.image.read(name)
        return PlainTextResponse(contents.decode("utf-8"))
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e)) from e


@app.post("/write/{name}")
async def write_to_bucket(name: str, request: Request):
    """Write image content to bucket by name."""
    try:
        body = await request.body()
        suga.image.write(name, body)
        return PlainTextResponse(f"File '{name}' written to bucket.")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e)) from e
