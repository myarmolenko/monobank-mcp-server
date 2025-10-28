package dev.monobank.mcpserver.dto;

import java.math.BigDecimal;

public record JarResponse(
        String id,
        String sendId,
        String title,
        String description,
        int currencyCode,
        BigDecimal balance,
        BigDecimal goal
) {

}
