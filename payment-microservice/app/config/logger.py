import logging
import json
from logging import Formatter

class JsonFormatter(Formatter):
    def __init__(self) -> None:
        super(JsonFormatter, self).__init__()

    def format(self, record): 
        json_record = {}
        json_record["message"] = record.getMessage()
        if "correlation_id" in record.__dict__:
            json_record["correlation_id"] = record.__dict__["correlation_id"]
        return json.dumps(json_record)
    
logger = logging.root
handler = logging.StreamHandler()
handler.setFormatter(JsonFormatter())
logger.handlers = [handler]
logger.setLevel(logging.INFO)