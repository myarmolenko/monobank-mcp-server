package dev.monobank.mcpserver.exception;

public class MetadataNotFoundException extends RuntimeException {
    public MetadataNotFoundException(String path, Throwable cause) {
        super("Metadata resource not found or unreadable: " + path, cause);
    }
}
