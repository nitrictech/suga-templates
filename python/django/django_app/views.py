"""Django views for file storage operations."""

import asyncio
import traceback
from django.http import HttpResponse, JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.views.decorators.http import require_http_methods
from suga_gen.client import SugaClient

# Initialize Suga client
suga = SugaClient()


@require_http_methods(["GET"])
def hello(request):
    """Return a simple hello world response."""
    return HttpResponse("Hello, World!", content_type="text/plain")


@require_http_methods(["GET"])
def read_from_bucket(request, name):
    """Read file content from bucket by name."""
    try:
        contents = suga.files.read(name)
        return HttpResponse(contents.decode("utf-8"), content_type="text/plain")
    except Exception as e:
        return JsonResponse({"detail": str(e)}, status=500)


@csrf_exempt
@require_http_methods(["POST"])
def write_to_bucket(request, name):
    """Write file content to bucket by name."""
    try:
            body = request.body
            suga.files.write(name, body)
            return HttpResponse(f"File '{name}' written to bucket.", content_type="text/plain")

    except Exception as e:
        traceback.print_exc()
        return JsonResponse({"detail": str(e)}, status=500) 