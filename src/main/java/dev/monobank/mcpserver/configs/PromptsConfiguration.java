package dev.monobank.mcpserver.configs;

import dev.monobank.mcpserver.prompt.PromptResolver;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static dev.monobank.mcpserver.prompt.PromptEnum.ACCOUNT_OVERVIEW;

@Configuration
@RequiredArgsConstructor
public class PromptsConfiguration {

    private final PromptResolver promptResolver;

    @Bean
    public List<McpServerFeatures.SyncPromptSpecification> myPrompts(McpServerFeatures.SyncPromptSpecification accountOverviewPromptSpecification) {
        return List.of(accountOverviewPromptSpecification);
    }

    @Bean
    public McpServerFeatures.SyncPromptSpecification accountOverviewPromptSpecification(McpSchema.Prompt accountOverviewPrompt) {
        return new McpServerFeatures.SyncPromptSpecification(
                accountOverviewPrompt,
                (exchange, getPromptRequest) -> {
                    List<McpSchema.PromptMessage> promptMessages = List.of(
                            new McpSchema.PromptMessage(
                                    McpSchema.Role.USER,
                                    new McpSchema.TextContent(promptResolver.resolvePrompt(ACCOUNT_OVERVIEW))
                            )
                    );

                    return new McpSchema.GetPromptResult("Personalized account overview", promptMessages);
                });
    }

    @Bean
    public McpSchema.Prompt accountOverviewPrompt() {
        return new McpSchema.Prompt(
                "monobank://account-overview",
                "Generates an overview of a user’s Monobank account",
                null
        );
    }
}
