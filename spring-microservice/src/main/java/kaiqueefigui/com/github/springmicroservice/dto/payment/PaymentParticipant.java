package kaiqueefigui.com.github.springmicroservice.dto.payment;

import kaiqueefigui.com.github.springmicroservice.dto.ParticipantRequest;

public record PaymentParticipant(Long id, String document, String account, String agency) {

    public PaymentParticipant(ParticipantRequest participant) {
        this(participant.id(), participant.document(), participant.account(), participant.agency());
    }
}
