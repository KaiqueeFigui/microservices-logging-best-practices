package kaiqueefigui.com.github.springmicroservice.dto.payment;

import java.math.BigDecimal;

public record PaymentTransaction(Long id, BigDecimal value, String description, Long payer_id, Long receiver_id) {
}
