package kaiqueefigui.com.github.springmicroservice.service;

import kaiqueefigui.com.github.springmicroservice.config.MDCProperties;
import kaiqueefigui.com.github.springmicroservice.dto.RegistryRequest;
import kaiqueefigui.com.github.springmicroservice.dto.payment.PaymentException;
import kaiqueefigui.com.github.springmicroservice.dto.payment.PaymentTransaction;
import kaiqueefigui.com.github.springmicroservice.entities.Participant;
import kaiqueefigui.com.github.springmicroservice.entities.Registry;
import kaiqueefigui.com.github.springmicroservice.enums.StatusEnum;
import kaiqueefigui.com.github.springmicroservice.repository.RegistryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Log4j2
public class RegistryService {

    @Value("${payment.url}")
    private String paymentUrl;
    private final ParticipantService participantService;
    private final RegistryRepository registryRepository;

    public void saveRegistry(RegistryRequest registryRequest) {
        log.info("Create Registry");
        try {
            var payer = participantService.findById(registryRequest.idPayer());
            var receiver = participantService.findById(registryRequest.idReceiver());
            var registry = toEntity(registryRequest, payer, receiver);
            registryRepository.save(registry);
        } catch (NoSuchElementException ex) {
            log.error("Could not save a Registry when payer or receiver not found");
            throw ex;
        }
    }

    public void payRegistry(Long registryId) {
        log.info("Pay registry. ID: {}", registryId);
        var registry = registryRepository.findById(registryId).orElseThrow();
        registry.setStatus(StatusEnum.PAYMENT_IN_PROGRESS.getEntity());
        registryRepository.save(registry);
        try {
            var rest = new RestTemplate();
            var headers = new HttpHeaders();
            headers.add(MDCProperties.CORRELATION_ID, MDC.get(MDCProperties.CORRELATION_ID));
            var httpEntity = new HttpEntity<>(new PaymentTransaction(null,
                    registry.getValue(),
                    "Registry payment",
                    registry.getPayer().getPaymentId(),
                    registry.getReceiver().getPaymentId()), headers);
            rest.exchange(paymentUrl + "/transaction/transfer", HttpMethod.POST, httpEntity, Void.class);
            registry.setStatus(StatusEnum.PAID.getEntity());
        } catch (HttpClientErrorException ex) {
            registry.setStatus(StatusEnum.PAYMENT_ERROR.getEntity());
            log.error("Could not made payment. Cause: {}", ex.getResponseBodyAs(PaymentException.class).getDetail());
        }
        registryRepository.save(registry);
    }

    private Registry toEntity(RegistryRequest registryRequest, Participant payer, Participant receiver) {
        return Registry.builder()
                .value(registryRequest.value())
                .payer(payer)
                .receiver(receiver)
                .status(StatusEnum.NOT_PAID.getEntity())
                .dueDate(registryRequest.dueDate())
                .build();
    }
}
