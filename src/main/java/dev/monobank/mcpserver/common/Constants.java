package dev.monobank.mcpserver.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Cache {
        public static final String STATEMENTS = "statements";
        public static final String CURRENCIES = "currencies";
        public static final String CLIENT_INFORMATION = "clientInformation";

        public static final String PROMPTS = "prompts";
        public static final String METADATA = "metadata";
    }
}