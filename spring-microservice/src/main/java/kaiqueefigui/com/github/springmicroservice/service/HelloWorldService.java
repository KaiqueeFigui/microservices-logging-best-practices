package kaiqueefigui.com.github.springmicroservice.service;

import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class HelloWorldService {
    public String sayHello() {
        log.info("Returning hello from service");
        return "hello";
    }

    public void fakeBadCall() {
        MDC.put("errorCause", "User made a bad request");
        var ex = new IllegalArgumentException("Exception from Hello World Service");
        log.error("About to throw IllegalArgumentException...", ex);
        throw ex;
    }
}
