FROM python:3.12-slim-bookworm AS base

# Build stage - has ALL dependencies
FROM base AS builder

COPY --from=ghcr.io/astral-sh/uv:0.4.9 /uv /bin/uv

ENV UV_COMPILE_BYTECODE=1 \
  UV_LINK_MODE=copy

WORKDIR /app

# Install dependencies first (for better caching)
COPY uv.lock pyproject.toml /app/

RUN --mount=type=cache,target=/root/.cache/uv \
  uv sync --frozen --no-install-project --no-dev

# Copy and build the application
COPY . /app

RUN --mount=type=cache,target=/root/.cache/uv \
  uv sync --frozen --no-dev

# Production stage - only runtime dependencies
FROM base

# Copy built application from the builder stage
COPY --from=builder /app /app

ENV PATH="/app/.venv/bin:$PATH"

# Start the app
CMD ["python", "-m", "main"]
