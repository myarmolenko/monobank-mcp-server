package dev.monobank.mcpserver.prompt;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PromptWarmup {
    private final PromptResolver resolver;

    @EventListener(ApplicationReadyEvent.class)
    public void warm() {
        for (var prompt : PromptEnum.values()) {
            try { resolver.resolvePrompt(prompt); } catch (Exception ignored) {}
        }
    }
}