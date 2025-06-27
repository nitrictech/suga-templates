# Base image for both build and final stages
FROM python:3.12-slim-bookworm AS base

# Builder stage for installing dependencies
FROM base AS builder

# Copy uv binary from official Astral image
COPY --from=ghcr.io/astral-sh/uv:0.4.9 /uv /bin/uv

# Optional uv environment config
ENV UV_COMPILE_BYTECODE=1 \
    UV_LINK_MODE=copy

# Set working directory
WORKDIR /app

# Copy lock and project files first (better cache layer)
COPY uv.lock pyproject.toml /app/

# Sync dependencies into isolated environment (.venv), no project install
RUN --mount=type=cache,target=/root/.cache/uv \
  uv sync --frozen --no-install-project --no-dev

# Copy source code
COPY . /app

# Sync again to install the app itself
RUN --mount=type=cache,target=/root/.cache/uv \
  uv sync --frozen --no-dev

# Final image, minimal runtime
FROM base

# Copy full app + .venv from builder
COPY --from=builder /app /app

# Activate virtualenv
ENV PATH="/app/.venv/bin:$PATH"

# Launch app (adjust if your module path changes)
CMD ["uvicorn", "uv_docker_example:app", "--host", "0.0.0.0", "--port", "$PORT"]
