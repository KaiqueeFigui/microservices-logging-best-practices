from uuid import uuid4

from starlette.middleware.base import BaseHTTPMiddleware, RequestResponseEndpoint
from starlette.requests import Request

from .LoggingContextHadler import ContextFilter
from .logger import logger


CORRELATION_ID = "correlation_id"

class RequestContextMiddleware(BaseHTTPMiddleware):

    async def dispatch(self, request: Request, call_next: RequestResponseEndpoint):
        correlation_id_header = request.headers.get(CORRELATION_ID)

        correlation_id = ""
        if correlation_id_header is None:
            correlation_id = str(uuid4())
        else:
            correlation_id = correlation_id_header
        
        cf = ContextFilter(filter_name="correlation_id_filter", extra={"correlation_id": correlation_id, "app": "payment"})
        logger.addFilter(cf)

        response = await call_next(request)

        return response