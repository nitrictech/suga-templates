"""Gunicorn configuration for production Django deployment."""
import os

# Bind to dynamic PORT from environment
port = os.environ.get("PORT", "8000")
bind = f"0.0.0.0:{port}"

# Worker configuration
workers = 4
worker_class = "sync"

# Logging
accesslog = "-"
errorlog = "-"
loglevel = "info"
