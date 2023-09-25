package kaiqueefigui.com.github.springmicroservice.service;

import kaiqueefigui.com.github.springmicroservice.config.MDCProperties;
import kaiqueefigui.com.github.springmicroservice.dto.payment.PaymentParticipant;
import kaiqueefigui.com.github.springmicroservice.dto.payment.PaymentTransaction;
import kaiqueefigui.com.github.springmicroservice.repository.ParticipantRepository;
import kaiqueefigui.com.github.springmicroservice.dto.ParticipantRequest;
import kaiqueefigui.com.github.springmicroservice.entities.Participant;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
@RequiredArgsConstructor
public class ParticipantService {

    @Value("${payment.url}")
    private String paymentUrl;
    private final ParticipantRepository participantRepository;

    public void createParticipant(ParticipantRequest participantRequest) {
        log.info("Create Participant");
        try {
            var rest = new RestTemplate();
            var headers = new HttpHeaders();
            headers.add(MDCProperties.CORRELATION_ID, MDC.get(MDCProperties.CORRELATION_ID));
            var httpEntity = new HttpEntity<>(new PaymentParticipant(participantRequest), headers);
            var response = rest.exchange(
                    paymentUrl + "/participant",
                    HttpMethod.POST,
                    httpEntity,
                    PaymentParticipant.class);
            var participant = toEntity(participantRequest);
            participant.setPaymentId(response.getBody().id());
            participantRepository.save(participant);
        } catch (RestClientException ex) {
            log.error("Could not create Participant in payment service, {}", ex.getMessage());
            throw new InternalError(ex.getMessage());
        }
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
