package dev.monobank.mcpserver.provider;

import dev.monobank.mcpserver.domain.ClientInfo;
import dev.monobank.mcpserver.domain.Currency;
import dev.monobank.mcpserver.domain.Statement;
import dev.monobank.mcpserver.service.MonobankService;
import lombok.RequiredArgsConstructor;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static dev.monobank.mcpserver.common.Constants.Defaults.DEFAULT_ACCOUNT_ID;
import static dev.monobank.mcpserver.common.Constants.Defaults.DEFAULT_RETRIEVAL_RANGE_DAYS;
import static dev.monobank.mcpserver.common.Constants.Defaults.DEFAULT_RETRIEVAL_TO_TIME;

@Service
@RequiredArgsConstructor
public class ToolProvider {
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
            description = """
                    Retrieve a list of statements between fromTime and toTime (Unix epoch seconds, UTC).
                    Constraints:
                    - Maximum range: 31 days.
                    Defaults:
                    - If accountId is blank, the main account is used.
                    - If fromTime is null/0, default is now-30 days.
                    - If toTime is null/0, default is now.
                    """
    )
    public List<Statement> retrieveClientStatements(
            @McpToolParam(required = false, description = "Monobank accountId ID (\"0\" for main);") final String accountId,
            @McpToolParam(required = false, description = "Start time in Unix epoch seconds (UTC);") final Long fromTime,
            @McpToolParam(required = false, description = "End time in Unix epoch seconds (UTC);") final Long toTime
    ) {
        final String effectiveAccount = Optional.ofNullable(accountId)
                .filter(id -> !id.isBlank())
                .orElse(DEFAULT_ACCOUNT_ID);
        final long effectiveFromTime = Optional.ofNullable(fromTime)
                .filter(time -> time > 0)
                .orElse(Instant.now().minus(DEFAULT_RETRIEVAL_RANGE_DAYS, ChronoUnit.DAYS).toEpochMilli());
        final long effectiveToTime = Optional.ofNullable(toTime)
                .filter(time -> time > 0)
                .filter(time -> time <= effectiveFromTime + Duration.ofDays(DEFAULT_RETRIEVAL_RANGE_DAYS).getSeconds())
                .orElse(DEFAULT_RETRIEVAL_TO_TIME);

        return monobankService.retrieveStatements(effectiveAccount, effectiveFromTime, effectiveToTime);
    }
}
