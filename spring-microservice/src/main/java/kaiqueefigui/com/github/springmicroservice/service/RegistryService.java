package kaiqueefigui.com.github.springmicroservice.service;

import kaiqueefigui.com.github.springmicroservice.dto.RegistryRequest;
import kaiqueefigui.com.github.springmicroservice.entities.Participant;
import kaiqueefigui.com.github.springmicroservice.entities.Registry;
import kaiqueefigui.com.github.springmicroservice.repository.RegistryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Log4j2
public class RegistryService {

    private final ParticipantService participantService;
    private final RegistryRepository registryRepository;

    public void saveRegistry(RegistryRequest registryRequest) {
        try {
            var payer = participantService.findById(registryRequest.idPayer());
            var receiver = participantService.findById(registryRequest.idReceiver());
            var registry = toEntity(registryRequest, payer, receiver);
            registryRepository.save(registry);
        } catch (NoSuchElementException ex) {
            log.error("Could not save registry when payer or receiver are not found");
            throw ex;
        }
    }

    private Registry toEntity(RegistryRequest registryRequest, Participant payer, Participant receiver) {
        return Registry.builder()
                .id(registryRequest.id())
                .value(registryRequest.value())
                .payer(payer)
                .receiver(receiver)
                .build();
    }
}
