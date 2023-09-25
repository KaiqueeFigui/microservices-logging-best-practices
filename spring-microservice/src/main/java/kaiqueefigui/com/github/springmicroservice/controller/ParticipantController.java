package kaiqueefigui.com.github.springmicroservice.controller;

import kaiqueefigui.com.github.springmicroservice.dto.ParticipantRequest;
import kaiqueefigui.com.github.springmicroservice.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping
    public ResponseEntity<Void> createParticipant(@RequestBody ParticipantRequest participantRequest) {
        participantService.createParticipant(participantRequest);
        return ResponseEntity.status(200).build();
    }
}
