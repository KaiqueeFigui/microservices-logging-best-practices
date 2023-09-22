package kaiqueefigui.com.github.springmicroservice.controller;

import kaiqueefigui.com.github.springmicroservice.dto.RegistryRequest;
import kaiqueefigui.com.github.springmicroservice.service.RegistryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class RegistryController {

    private final RegistryService registryService;

    public ResponseEntity<Void> createParticipant(RegistryRequest registryRequest) {
        registryService.saveRegistry(registryRequest);
        return ResponseEntity.status(200).build();
    }
}
