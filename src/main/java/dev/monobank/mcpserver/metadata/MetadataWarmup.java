package dev.monobank.mcpserver.metadata;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MetadataWarmup {
    private final MetadataResolver resolver;

    @EventListener(ApplicationReadyEvent.class)
    public void warm() {
        for (var metadata : MetadataEnum.values()) {
            try { resolver.resolveMetadata(metadata); } catch (Exception ignored) {}
        }
    }
}
