import logging
import json
from logging import Formatter
import logstash

host = 'logstash'

class JsonFormatter(Formatter):
    def __init__(self) -> None:
        super(JsonFormatter, self).__init__()

    def format(self, record): 
        json_record = {}
        json_record["message"] = record.getMessage()
        if "correlation_id" in record.__dict__:
            json_record["correlation_id"] = record.__dict__["correlation_id"]
        return json.dumps(json_record)
    
logger = logging.getLogger('python-logstash-logger')
handler = logging.StreamHandler()
handler.setFormatter(JsonFormatter())
logstash_hadler = logstash.TCPLogstashHandler(host, 5000, version=1)
logger.addHandler(handler)
logger.addHandler(logstash_hadler)
logger.setLevel(logging.INFO)