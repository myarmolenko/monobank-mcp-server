package dev.monobank.mcpserver.mapper;

import dev.monobank.mcpserver.configs.CentralMappingConfiguration;
import dev.monobank.mcpserver.domain.Currency;
import dev.monobank.mcpserver.dto.CurrencyResponse;
import dev.monobank.mcpserver.mapper.qualifier.TimestampToDate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", config = CentralMappingConfiguration.class)
public interface CurrencyMapper {

    @Mapping(target = "baseCurrency", source = "currencyCodeA")
    @Mapping(target = "quoteCurrency", source = "currencyCodeB")
    @Mapping(target = "date", source = "date", qualifiedBy = TimestampToDate.class)
    Currency toCurrency(CurrencyResponse currencyResponse);

    List<Currency> toCurrencies(List<CurrencyResponse> currencyResponses);
}
