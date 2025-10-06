FROM python:3.12-slim-bookworm AS base

# Build stage - has ALL dependencies
FROM base AS builder

WORKDIR /app

# Install dependencies first (for better caching)
COPY requirements.txt /app/

RUN python -m venv /app/.venv && \
    /app/.venv/bin/pip install --no-cache-dir -r requirements.txt

# Production stage - only runtime dependencies
FROM base

WORKDIR /app

# Copy built application from the builder stage
COPY --from=builder /app/.venv /app/.venv
COPY . /app

ENV PATH="/app/.venv/bin:$PATH"

# Start the app
CMD ["gunicorn", "-c", "gunicorn.conf.py", "django_app.wsgi:application"]