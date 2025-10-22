package dev.monobank.mcpserver.domain;

import java.math.BigDecimal;
import java.util.List;

public record Account(
        String id,
        String type,
        String currencyCode,
        BigDecimal balance,
        BigDecimal creditLimit,
        String cashbackType,
        List<String> maskedPan,
        String iban
) {

}
