import logging

class ContextFilter(logging.Filter):
    def __init__(self, filter_name, extra):
        super(ContextFilter, self).__init__(filter_name)
        self.correlation_id = extra["correlation_id"]
        self.app = extra["app"]

    def filter(self, record):
        record.__dict__["correlation_id"] = self.correlation_id
        record.__dict__["app"] = self.app
        return True