package dev.monobank.mcpserver.domain;

import java.math.BigDecimal;

public record Jar(
        String title,
        String description,
        String currencyCode,
        BigDecimal balance,
        BigDecimal goal,
        int progressPercentage
) {

}
