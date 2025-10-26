package dev.monobank.mcpserver.service;

import dev.monobank.mcpserver.domain.ClientInfo;
import dev.monobank.mcpserver.domain.Currency;
import dev.monobank.mcpserver.domain.Statement;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static dev.monobank.mcpserver.common.Constants.Defaults.DEFAULT_ACCOUNT_ID;
import static dev.monobank.mcpserver.common.Constants.Defaults.DEFAULT_RETRIEVAL_TO_TIME;
import static dev.monobank.mcpserver.common.Constants.Defaults.DEFAULT_RETRIEVAL_RANGE_DAYS;

@Service
@RequiredArgsConstructor
public class MonobankToolService {

    private final MonobankService monobankService;

    @Tool(
            name = "retrieve_all_currencies",
            description = "Retrieves all currencies from Monobank API"
    )
    public List<Currency> retrieveAllCurrencies() {
        return monobankService.retrieveCurrencies();
    }

    @Tool(
            name = "retrieve_client_information",
            description = "Retrieves client information from Monobank API"
    )
    public ClientInfo retrieveClientInfo() {
        return monobankService.retrieveClientInformation();
    }

    @Tool(
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
            @ToolParam(required = false, description = "Monobank accountId ID (\"0\" for main);") final String accountId,
            @ToolParam(required = false, description = "Start time in Unix epoch seconds (UTC);") final Long fromTime,
            @ToolParam(required = false, description = "End time in Unix epoch seconds (UTC);") final Long toTime
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
