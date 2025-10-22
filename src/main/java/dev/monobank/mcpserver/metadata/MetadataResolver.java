package dev.monobank.mcpserver.metadata;

import dev.monobank.mcpserver.common.Constants;
import dev.monobank.mcpserver.exception.MetadataNotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class MetadataResolver {

    @Cacheable(
            value = Constants.Cache.METADATA,
            key = "#metadata.name()",
            unless = "#result == null || #result.isBlank()",
            cacheManager = "resourceCacheManager"
    )
    public String resolveMetadata(MetadataEnum metadata) {
        final var resource = new ClassPathResource(metadata.getPath());
        try (final var resourceInputStream = resource.getInputStream()) {
            return new String(resourceInputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new MetadataNotFoundException(metadata.getPath(), ex);
        }
    }
}
