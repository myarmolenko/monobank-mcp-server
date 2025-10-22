package dev.monobank.mcpserver.dto;

import java.math.BigDecimal;

public record StatementResponse(
        String id,
        long time,
        String description,
        int mcc,
        int originalMcc,
        boolean hold,
        long amount,
        long operationAmount,
        int currencyCode,
        int commissionRate,
        BigDecimal cashbackAmount,
        long balance,
        String comment,
        String receiptId,
        String invoiceId,
        String counterEdrpou,
        String counterIban,
        String counterName
) {

}
