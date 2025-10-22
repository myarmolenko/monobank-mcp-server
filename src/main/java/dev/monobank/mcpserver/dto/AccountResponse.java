package dev.monobank.mcpserver.dto;

import java.math.BigDecimal;

public record AccountResponse(
        String id,
        String sendId,
        int currencyCode,
        String cashbackType,
        BigDecimal balance,
        BigDecimal creditLimit,
        String[] maskedPan,
        String type,
        String iban
) {

}
