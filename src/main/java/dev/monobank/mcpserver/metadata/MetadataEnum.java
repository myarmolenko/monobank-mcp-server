package dev.monobank.mcpserver.metadata;

import lombok.Getter;

@Getter
public enum MetadataEnum {
    CLIENT_INFO("metadata/client-info.md", "text/markdown"),
    CURRENCY("metadata/currency.md", "text/markdown"),
    STATEMENT("metadata/statement.md", "text/markdown");

    private final String path;
    private final String mimeType;

    MetadataEnum(String path, String mimeType) {
        this.path = path;
        this.mimeType = mimeType;
    }
}