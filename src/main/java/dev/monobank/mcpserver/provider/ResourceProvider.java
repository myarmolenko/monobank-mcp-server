package dev.monobank.mcpserver.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.monobank.mcpserver.domain.ClientInfo;
import dev.monobank.mcpserver.domain.Currency;
import dev.monobank.mcpserver.domain.Statement;
import dev.monobank.mcpserver.metadata.MetadataResolver;
import dev.monobank.mcpserver.service.MonobankService;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.RequiredArgsConstructor;
import org.springaicommunity.mcp.annotation.McpResource;
import org.springframework.stereotype.Service;
import io.modelcontextprotocol.spec.McpSchema.*;

import java.util.List;

import static dev.monobank.mcpserver.metadata.MetadataEnum.CLIENT_INFO;
import static dev.monobank.mcpserver.metadata.MetadataEnum.CURRENCY;
import static dev.monobank.mcpserver.metadata.MetadataEnum.STATEMENT;

@Service
@RequiredArgsConstructor
public class ResourceProvider {
    private final MonobankService monobankService;
    private final MetadataResolver metadataResolver;
    private final ObjectMapper mapper;

    @McpResource(
            uri = "monobank://personal/client-info",
            name = "Monobank Client Info",
            description = "Monobank personal client info"
    )
    public ReadResourceResult monobankClientInformation(ReadResourceRequest request) {
        ClientInfo clientInfo = monobankService.retrieveClientInformation();

        try {
            return new McpSchema.ReadResourceResult(List.of(
                    new McpSchema.TextResourceContents(
                            request.uri(),
                            "application/json",
                            mapper.writeValueAsString(clientInfo)),
                    new McpSchema.TextResourceContents(
                            request.uri() + "#explanation",
                            CLIENT_INFO.getMimeType(),
                            metadataResolver.resolveMetadata(CLIENT_INFO))
            ));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize resource: " + request.uri());
        }
    }

    @McpResource(
            uri = "monobank://bank/currency",
            name = "Monobank Currencies",
            description = "Monobank public currency rates (cached by bank ~5 min)."
    )
    public ReadResourceResult monobankCurrencies(ReadResourceRequest request) {
        List<Currency> currencies = monobankService.retrieveCurrencies();

        try {
            List<ResourceContents> resources = List.of(
                    new TextResourceContents(
                            request.uri(),
                            "application/json",
                            mapper.writeValueAsString(currencies)),
                    new TextResourceContents(
                            request.uri() + "#explanation",
                            CURRENCY.getMimeType(),
                            metadataResolver.resolveMetadata(CURRENCY))
            );

            return new ReadResourceResult(resources);
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
                            mapper.writeValueAsString(statements)),
                    new TextResourceContents(
                            request.uri() + "#explanation",
                            STATEMENT.getMimeType(),
                            metadataResolver.resolveMetadata(STATEMENT))
            );

            return new ReadResourceResult(resources);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize resource: " + request.uri());
        }
    }
}
