package dev.monobank.mcpserver.provider;

import dev.monobank.mcpserver.prompt.PromptResolver;
import lombok.RequiredArgsConstructor;
import org.springaicommunity.mcp.annotation.McpPrompt;
import org.springframework.stereotype.Service;
import io.modelcontextprotocol.spec.McpSchema.*;

import java.util.List;

import static dev.monobank.mcpserver.prompt.PromptEnum.ACCOUNT_OVERVIEW;
import static dev.monobank.mcpserver.prompt.PromptEnum.INTERPRET_MONOBANK_CLIENT_INFO;
import static dev.monobank.mcpserver.prompt.PromptEnum.INTERPRET_MONOBANK_CURRENCY;
import static dev.monobank.mcpserver.prompt.PromptEnum.INTERPRET_MONOBANK_STATEMENT;

@Service
@RequiredArgsConstructor
public class PromptProvider {
    private final PromptResolver promptResolver;

    @McpPrompt(name = "account-overview", description = "Generates an overview of a user’s Monobank account")
    public GetPromptResult accountOverviewPrompt() {
        return new GetPromptResult("Personalized Account Overview",
                List.of(
                        new PromptMessage(Role.USER, new TextContent(promptResolver.resolvePrompt(ACCOUNT_OVERVIEW)))
                )
        );
    }

    @McpPrompt(
            name = "Interpret Monobank Statements",
            description = "Rules for interpreting and reasoning about Monobank transaction statements (single or multiple)"
    )
    public GetPromptResult interpretMonobankStatementsPrompt() {
        return new GetPromptResult("Rules for interpreting and reasoning about Monobank transaction statements (single or multiple)",
                List.of(
                        new PromptMessage(Role.ASSISTANT, new TextContent(promptResolver.resolvePrompt(INTERPRET_MONOBANK_STATEMENT)))
                )
        );
    }

    @McpPrompt(
            name = "Interpret Monobank Currency Record",
            description = "Rules for interpreting a Monobank currency record"
    )
    public GetPromptResult interpretMonobankCurrencyPrompt() {
        return new GetPromptResult("Rules for interpreting a Monobank currency record",
                List.of(
                        new PromptMessage(Role.ASSISTANT, new TextContent(promptResolver.resolvePrompt(INTERPRET_MONOBANK_CURRENCY)))
                )
        );
    }

    @McpPrompt(
            name = "Interpret Monobank Client Information",
            description = "Shared rules for reasoning about Monobank client data (accounts, jars, client info)"
    )
    public GetPromptResult interpretMonobankClientInfoPrompt() {
        return new GetPromptResult("Shared rules for reasoning about Monobank client data (accounts, jars, client info)",
                List.of(
                        new PromptMessage(Role.ASSISTANT, new TextContent(promptResolver.resolvePrompt(INTERPRET_MONOBANK_CLIENT_INFO)))
                )
        );
    }
}
