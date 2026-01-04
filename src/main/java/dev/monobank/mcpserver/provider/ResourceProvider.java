package dev.monobank.mcpserver.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.monobank.mcpserver.domain.ClientInfo;
import dev.monobank.mcpserver.domain.Currency;
import dev.monobank.mcpserver.domain.Statement;
import dev.monobank.mcpserver.service.MonobankService;
import lombok.RequiredArgsConstructor;
import org.springaicommunity.mcp.annotation.McpResource;
import org.springframework.stereotype.Service;
import io.modelcontextprotocol.spec.McpSchema.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceProvider {
    private final MonobankService monobankService;
    private final ObjectMapper mapper;

    @McpResource(
            uri = "monobank://bank/currency-rates",
            name = "Monobank Currency Rates",
            description = "Monobank public currency rates (cached by bank ~5 min)."
    )
    public ReadResourceResult monobankCurrencies(ReadResourceRequest request) {
        List<Currency> currencies = monobankService.retrieveCurrencies();

        try {
            List<ResourceContents> resources = List.of(
                    new TextResourceContents(
                            request.uri(),
                            "application/json",
                            mapper.writeValueAsString(currencies))
            );

            return new ReadResourceResult(resources);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize resource: " + request.uri());
        }
    }

    @McpResource(
            uri = "monobank://personal/client-info",
            name = "Monobank Client Info",
            description = "Monobank personal client info"
    )
    public ReadResourceResult monobankClientInformation(ReadResourceRequest request) {
        ClientInfo clientInfo = monobankService.retrieveClientInformation();

        try {
            return new ReadResourceResult(List.of(
                    new TextResourceContents(
                            request.uri(),
                            "application/json",
                            mapper.writeValueAsString(clientInfo))
            ));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize resource: " + request.uri());
        }
    }

    @McpResource(
            uri = "monobank://personal/statements",
            name = "Monobank Statements",
            description = "Monobank statements information"
    )
    public ReadResourceResult monobankStatements(ReadResourceRequest request) {
        List<Statement> statements = monobankService.retrieveStatements();

        try {
            List<ResourceContents> resources = List.of(
                    new TextResourceContents(
                            request.uri(),
                            "application/json",
                            mapper.writeValueAsString(statements))
            );

            return new ReadResourceResult(resources);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize resource: " + request.uri());
        }
    }
}
