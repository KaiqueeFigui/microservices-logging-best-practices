package kaiqueefigui.com.github.springmicroservice.controller;

import kaiqueefigui.com.github.springmicroservice.dto.RegistryRequest;
import kaiqueefigui.com.github.springmicroservice.service.RegistryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/registries")
public class RegistryController {

    private final RegistryService registryService;

    @PostMapping
    public ResponseEntity<Void> createParticipant(@RequestBody RegistryRequest registryRequest) {
        registryService.saveRegistry(registryRequest);
        return ResponseEntity.status(200).build();
    }

    @PostMapping("/{registryId}")
    public ResponseEntity<Void> payRegistry(@PathVariable Long registryId) {
        registryService.payRegistry(registryId);
        return ResponseEntity.status(200).build();
    }
}
