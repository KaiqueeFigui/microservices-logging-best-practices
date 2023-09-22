package kaiqueefigui.com.github.springmicroservice.dto;

import java.math.BigDecimal;

public record RegistryRequest (
        Long id, BigDecimal value, Long idPayer, Long idReceiver
) {
}
