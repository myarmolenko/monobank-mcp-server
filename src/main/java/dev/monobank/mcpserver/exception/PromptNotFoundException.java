package dev.monobank.mcpserver.exception;

public class PromptNotFoundException extends RuntimeException {
    public PromptNotFoundException(String path, Throwable cause) {
        super("Prompt resource not found or unreadable: " + path, cause);
    }
}
