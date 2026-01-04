package dev.monobank.mcpserver.service;

import dev.monobank.mcpserver.client.MonobankClient;
import dev.monobank.mcpserver.domain.ClientInfo;
import dev.monobank.mcpserver.domain.Currency;
import dev.monobank.mcpserver.domain.Statement;
import dev.monobank.mcpserver.dto.StatementResponse;
import dev.monobank.mcpserver.mapper.ClientInfoMapper;
import dev.monobank.mcpserver.mapper.CurrencyMapper;
import dev.monobank.mcpserver.mapper.StatementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static dev.monobank.mcpserver.common.Constants.Defaults.DEFAULT_ACCOUNT_ID;
import static dev.monobank.mcpserver.common.Constants.Defaults.DEFAULT_RETRIEVAL_RANGE_DAYS;

@Service
@RequiredArgsConstructor
public class MonobankService {

    private final MonobankClient monobankClient;
    private final StatementMapper statementMapper;
    private final ClientInfoMapper clientInfoMapper;
    private final CurrencyMapper currencyMapper;

    public ClientInfo retrieveClientInformation() {
        return clientInfoMapper.toClientInfo(monobankClient.retrieveClientInformation());
    }

    public List<Currency> retrieveCurrencies() {
        return currencyMapper.toCurrencies(monobankClient.retrieveCurrencies());
    }

    public List<Statement> retrieveStatements() {
        final long fromTime = Instant.now().minus(DEFAULT_RETRIEVAL_RANGE_DAYS, ChronoUnit.DAYS).toEpochMilli();
        return retrieveStatements(DEFAULT_ACCOUNT_ID, fromTime, null);
    }

    public List<Statement> retrieveStatements(final String account, final Long fromTime, final Long toTime) {
        final List<StatementResponse> response = monobankClient.retrieveStatements(account, fromTime, toTime);
        return statementMapper.toStatements(response);
    }

}
