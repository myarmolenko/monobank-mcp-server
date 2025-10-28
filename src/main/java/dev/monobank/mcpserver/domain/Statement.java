package dev.monobank.mcpserver.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Statement(
        LocalDateTime date,
        int category,
        int currencyCode,
        BigDecimal amount,
        BigDecimal balance,
        BigDecimal cashback,
        String merchant,
        String description,
        String notes
) {

}
