package dev.monobank.mcpserver.mapper.converter;

import dev.monobank.mcpserver.mapper.qualifier.MinorToMajor;

import java.math.BigDecimal;

public class MoneyConverters {

    @MinorToMajor
    public static BigDecimal minorToMajor(BigDecimal amount) {
        return amount == null ? BigDecimal.ZERO : amount.divide(BigDecimal.valueOf(100));
    }
}
