package dev.monobank.mcpserver.provider;

import dev.monobank.mcpserver.prompt.PromptResolver;
import lombok.RequiredArgsConstructor;
import org.springaicommunity.mcp.annotation.McpPrompt;
import org.springframework.stereotype.Service;
import io.modelcontextprotocol.spec.McpSchema.*;

import java.util.List;

import static dev.monobank.mcpserver.prompt.PromptEnum.ACCOUNT_OVERVIEW;

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
}
