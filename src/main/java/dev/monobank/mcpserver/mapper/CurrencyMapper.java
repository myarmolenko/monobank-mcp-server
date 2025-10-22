package dev.monobank.mcpserver.mapper;

import dev.monobank.mcpserver.domain.Currency;
import dev.monobank.mcpserver.dto.CurrencyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    @Mapping(target = "baseCurrency", source = "currencyCodeA")
    @Mapping(target = "quoteCurrency", source = "currencyCodeB")
    @Mapping(target = "date", source = "date", qualifiedByName = "timestampToDate")
    Currency toCurrency(CurrencyResponse currencyResponse);

    List<Currency> toCurrencies(List<CurrencyResponse> currencyResponses);

    @Named("timestampToDate")
    default LocalDateTime timestampToDate(long timestamp) {
        return Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
