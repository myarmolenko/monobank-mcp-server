package dev.monobank.mcpserver.exception;

public class MonobankRateLimitException extends RuntimeException {
    public MonobankRateLimitException(String message) {
        super(message);
    }
}
