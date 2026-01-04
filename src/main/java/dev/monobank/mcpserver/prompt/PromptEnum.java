package dev.monobank.mcpserver.prompt;

import lombok.Getter;

@Getter
public enum PromptEnum {
    ACCOUNT_OVERVIEW("prompts/account-overview.md"),
    INTERPRET_MONOBANK_STATEMENT("prompts/interpret-monobank-statement.md"),
    INTERPRET_MONOBANK_CURRENCY("prompts/interpret-monobank-currency.md"),
    INTERPRET_MONOBANK_CLIENT_INFO("prompts/interpret-monobank-client-info.md");

    private final String path;

    PromptEnum(String path) {
        this.path = path;
    }
}
