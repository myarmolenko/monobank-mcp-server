package dev.monobank.mcpserver.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.monobank.mcpserver.domain.ClientInfo;
import dev.monobank.mcpserver.domain.Currency;
import dev.monobank.mcpserver.domain.Statement;
import dev.monobank.mcpserver.metadata.MetadataResolver;
import dev.monobank.mcpserver.service.MonobankService;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static dev.monobank.mcpserver.metadata.MetadataEnum.*;

@Configuration
@RequiredArgsConstructor
public class ResourcesConfiguration {

    private final MonobankService monobankService;
    private final MetadataResolver metadataResolver;
    private final ObjectMapper mapper;

    @Bean
    public List<McpServerFeatures.SyncResourceSpecification> monobankResources(
            McpServerFeatures.SyncResourceSpecification monobankCurrenciesResourceSpecification,
            McpServerFeatures.SyncResourceSpecification monobankClientInformationResourceSpecification,
            McpServerFeatures.SyncResourceSpecification monobankStatementsResourceSpecification
    ) {
        return List.of(monobankCurrenciesResourceSpecification,
                monobankClientInformationResourceSpecification,
                monobankStatementsResourceSpecification);
    }

    @Bean
    public McpServerFeatures.SyncResourceSpecification monobankClientInformationResourceSpecification(McpSchema.Resource monobankClientInformationResource) {
        return new McpServerFeatures.SyncResourceSpecification(monobankClientInformationResource, (exchange, request) -> {
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
                throw new RuntimeException(e);
            }
        });
    }

    @Bean
    public McpServerFeatures.SyncResourceSpecification monobankCurrenciesResourceSpecification(McpSchema.Resource monobankCurrenciesResource) {
        return new McpServerFeatures.SyncResourceSpecification(monobankCurrenciesResource, (exchange, request) -> {
            List<Currency> currencies = monobankService.retrieveCurrencies();

            try {
                List<McpSchema.ResourceContents> resources = List.of(
                        new McpSchema.TextResourceContents(
                                request.uri(),
                                "application/json",
                                mapper.writeValueAsString(currencies)),
                        new McpSchema.TextResourceContents(
                                request.uri() + "#explanation",
                                CURRENCY.getMimeType(),
                                metadataResolver.resolveMetadata(CURRENCY))
                );

                return new McpSchema.ReadResourceResult(resources);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Bean
    public McpServerFeatures.SyncResourceSpecification monobankStatementsResourceSpecification(McpSchema.Resource monobankStatementsResource) {
        return new McpServerFeatures.SyncResourceSpecification(monobankStatementsResource, (exchange, request) -> {
            List<Statement> statements = monobankService.retrieveStatements();

            try {
                List<McpSchema.ResourceContents> resources = List.of(
                        new McpSchema.TextResourceContents(
                                request.uri(),
                                "application/json",
                                mapper.writeValueAsString(statements)),
                        new McpSchema.TextResourceContents(
                                request.uri() + "#explanation",
                                STATEMENT.getMimeType(),
                                metadataResolver.resolveMetadata(STATEMENT))
                );

                return new McpSchema.ReadResourceResult(resources);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Bean(name = "monobankClientInformationResource")
    public McpSchema.Resource monobankClientInformationResource() {
        return new McpSchema.Resource(
                "monobank://personal/client-info",
                "monobank-client-info",
                "Monobank personal client info",
                "application/json",
                null
        );
    }

    @Bean(name = "monobankCurrenciesResource")
    public McpSchema.Resource monobankCurrenciesResource() {
        return new McpSchema.Resource(
                "monobank://bank/currency",
                "monobank-currencies",
                "Monobank public currency rates (cached by bank ~5 min).",
                "application/json",
                null
        );
    }

    @Bean(name = "monobankStatementsResource")
    public McpSchema.Resource monobankStatementsResource() {
        return new McpSchema.Resource(
                "monobank://personal/statements",
                "monobank-statements",
                "Monobank statements information",
                "application/json",
                null
        );
    }
}
