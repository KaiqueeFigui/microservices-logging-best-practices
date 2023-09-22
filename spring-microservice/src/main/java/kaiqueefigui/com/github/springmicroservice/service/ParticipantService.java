package kaiqueefigui.com.github.springmicroservice.service;

import kaiqueefigui.com.github.springmicroservice.repository.ParticipantRepository;
import kaiqueefigui.com.github.springmicroservice.dto.ParticipantRequest;
import kaiqueefigui.com.github.springmicroservice.entities.Participant;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    public void createParticipant(ParticipantRequest participantRequest) {
        participantRepository.save(toEntity(participantRequest));
    }

    public Participant findById(Long id) {
        return participantRepository.findById(id).orElseThrow();
    }

    private Participant toEntity(ParticipantRequest participantRequest) {
        return Participant.builder()
                .id(participantRequest.id())
                .account(participantRequest.account())
                .agency(participantRequest.agency())
                .document(participantRequest.document())
                .build();
    }
}
