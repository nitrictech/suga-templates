# Base image for both build and final stages
FROM python:3.12-slim-bookworm AS base

# Builder stage for installing dependencies
FROM base AS builder

# Set working directory
WORKDIR /app

# Copy requirements file first (better cache layer)
COPY requirements.txt /app/

# Install dependencies
RUN --mount=type=cache,target=/root/.cache/pip \
  pip install --user -r requirements.txt

# Final image, minimal runtime
FROM base

# Copy dependencies from builder
COPY --from=builder /root/.local /root/.local

# Copy source code
COPY . /app

# Set working directory
WORKDIR /app

# Make sure scripts in .local are usable
ENV PATH=/root/.local/bin:$PATH

# Launch Django app
CMD ["python", "manage.py", "runserver", "0.0.0.0:$PORT"]