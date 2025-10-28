package dev.monobank.mcpserver.prompt;

import dev.monobank.mcpserver.common.Constants;
import dev.monobank.mcpserver.exception.PromptNotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class PromptResolver {

    @Cacheable(
            value = Constants.Cache.PROMPTS,
            key = "#prompt.name()",
            unless = "#result == null || #result.isBlank()",
            cacheManager = "resourceCacheManager"
    )
    public String resolvePrompt(final PromptEnum prompt) {
        final var resource = new ClassPathResource(prompt.getPath());
        try (final var resourceInputStream = resource.getInputStream()) {
            return new String(resourceInputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new PromptNotFoundException(prompt.getPath(), ex);
        }
    }
}
