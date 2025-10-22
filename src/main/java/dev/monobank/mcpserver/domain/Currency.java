package dev.monobank.mcpserver.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Currency(
        int baseCurrency,
        int quoteCurrency,
        LocalDateTime date,
        BigDecimal rateBuy,
        BigDecimal rateSell,
        BigDecimal rateCross
) {

}