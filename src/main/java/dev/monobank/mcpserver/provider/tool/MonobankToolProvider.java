package dev.monobank.mcpserver.provider.tool;

import dev.monobank.mcpserver.domain.ClientInfo;
import dev.monobank.mcpserver.domain.Currency;
import dev.monobank.mcpserver.domain.Statement;
import dev.monobank.mcpserver.service.MonobankService;
import lombok.RequiredArgsConstructor;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static dev.monobank.mcpserver.common.Constants.Defaults.DEFAULT_ACCOUNT_ID;

@Service
@RequiredArgsConstructor
public class MonobankToolProvider {
    private final MonobankService monobankService;

    @McpTool(
            name = "retrieve_all_currencies",
            description = "Retrieves all currencies from Monobank API"
    )
    public List<Currency> retrieveAllCurrencies() {
        return monobankService.retrieveCurrencies();
    }

    @McpTool(
            name = "retrieve_client_information",
            description = "Retrieves client information from Monobank API"
    )
    public ClientInfo retrieveClientInfo() {
        return monobankService.retrieveClientInformation();
    }

    @McpTool(
            name = "retrieve_statements",
            description = "Retrieve a list of statements between fromTime and toTime (Unix epoch seconds, UTC)."
    )
    public List<Statement> retrieveClientStatements(
            @McpToolParam(description = "Monobank accountId ID") final String accountId,
            @McpToolParam(description = "Start time in Unix epoch seconds (UTC).") final Long fromTime,
            @McpToolParam(description = "End time in Unix epoch seconds (UTC).") final Long toTime
    ) {
        if (fromTime == null || toTime == null) {
            throw new IllegalArgumentException("fromTime and toTime must be provided");
        }
        if (fromTime <= 0 || toTime <= 0) {
            throw new IllegalArgumentException("fromTime and toTime must be positive Unix epoch seconds");
        }
        if (fromTime > toTime) {
            throw new IllegalArgumentException("fromTime must be less than or equal to toTime");
        }

        final String effectiveAccountId = Optional.ofNullable(accountId)
                .filter(id -> !id.isBlank())
                .orElse(DEFAULT_ACCOUNT_ID);

        return monobankService.retrieveStatements(effectiveAccountId, fromTime, toTime);
    }
}
