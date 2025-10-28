package dev.monobank.mcpserver.dto;

public record CurrencyResponse(
        int currencyCodeA,
        int currencyCodeB,
        long date,
        double rateBuy,
        double rateSell,
        double rateCross
) {

}