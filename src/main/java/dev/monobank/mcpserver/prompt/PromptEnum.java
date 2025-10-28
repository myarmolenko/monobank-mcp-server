package dev.monobank.mcpserver.prompt;

import lombok.Getter;

@Getter
public enum PromptEnum {
    ACCOUNT_OVERVIEW("prompts/account-overview.md");

    private final String path;

    PromptEnum(String path) {
        this.path = path;
    }
}
