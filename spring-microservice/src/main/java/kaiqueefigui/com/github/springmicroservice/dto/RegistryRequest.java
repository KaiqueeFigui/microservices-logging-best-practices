package kaiqueefigui.com.github.springmicroservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RegistryRequest (BigDecimal value, Long idPayer, Long idReceiver, LocalDate dueDate) {
}
