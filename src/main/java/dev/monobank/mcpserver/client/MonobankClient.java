package dev.monobank.mcpserver.client;

import dev.monobank.mcpserver.common.Constants;
import dev.monobank.mcpserver.dto.ClientInfoResponse;
import dev.monobank.mcpserver.dto.CurrencyResponse;
import dev.monobank.mcpserver.dto.StatementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MonobankClient {

    private final RestClient monobankRestClient;

    @Cacheable(
            value = Constants.Cache.CLIENT_INFORMATION,
            key = "'client-info'",
            unless = "#result == null"
    )
    public ClientInfoResponse retrieveClientInformation() {
        return monobankRestClient.get()
                .uri("/personal/client-info")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    @Cacheable(
            value = Constants.Cache.CURRENCIES,
            key = "'currencies'",
            unless = "#result == null || #result.isEmpty()"
    )
    public List<CurrencyResponse> retrieveCurrencies() {
        return monobankRestClient.get()
                .uri("/bank/currency")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    @Cacheable(
            value = Constants.Cache.STATEMENTS,
            key = "#account + ':' + #fromTime + ':' + (#toTime ?: 0)",
            unless = "#result == null || #result.isEmpty()"
    )
    public List<StatementResponse> retrieveStatements(final String account, final Long fromTime, final Long toTime) {
        return monobankRestClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/personal/statement/{account}/{from}");
                    if (toTime != null && toTime != 0) {
                        uriBuilder.path("/{to}");
                    }
                    return uriBuilder.build(account, fromTime, toTime);
                })
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}
