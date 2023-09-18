package kaiqueefigui.com.github.springmicroservice.controller;

import kaiqueefigui.com.github.springmicroservice.service.HelloWorldService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@Log4j2
public class HelloWorldController {
    private final HelloWorldService helloWorldService;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        log.info("Someone called the /hello endpoint");
        return ResponseEntity.ok(helloWorldService.sayHello());
    }

    @GetMapping("/bad-call")
    public void badCall() {
        log.info("Someone called the /bad-call endpoint");
        helloWorldService.fakeBadCall();
    }
}
