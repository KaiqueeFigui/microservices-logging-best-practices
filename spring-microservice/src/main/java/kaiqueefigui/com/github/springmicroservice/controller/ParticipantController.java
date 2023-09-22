package kaiqueefigui.com.github.springmicroservice.controller;

import kaiqueefigui.com.github.springmicroservice.dto.ParticipantRequest;
import kaiqueefigui.com.github.springmicroservice.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class ParticipantController {

    private final ParticipantService participantService;

    public ResponseEntity<Void> createParticipant(ParticipantRequest participantRequest) {
        participantService.createParticipant(participantRequest);
        return ResponseEntity.status(200).build();
    }
}
