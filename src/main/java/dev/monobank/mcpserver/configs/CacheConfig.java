package dev.monobank.mcpserver.configs;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;
import java.util.List;

import static dev.monobank.mcpserver.common.Constants.Cache.CURRENCIES;
import static dev.monobank.mcpserver.common.Constants.Cache.STATEMENTS;
import static dev.monobank.mcpserver.common.Constants.Cache.CLIENT_INFORMATION;
import static dev.monobank.mcpserver.common.Constants.Cache.PROMPTS;
import static dev.monobank.mcpserver.common.Constants.Cache.METADATA;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    @Primary
    public CacheManager apiCacheManager() {
        var cacheManager = new CaffeineCacheManager(CURRENCIES, STATEMENTS, CLIENT_INFORMATION);
        cacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .expireAfterWrite(Duration.ofMinutes(1))
                        .maximumSize(100)
                        .recordStats()
        );
        return cacheManager;
    }

    @Bean
    public CacheManager resourceCacheManager() {
        var prompts = new CaffeineCache(
                PROMPTS,
                Caffeine.newBuilder()
                        .maximumSize(10)
                        .recordStats()
                        .build()
        );

        var metadata = new CaffeineCache(
                METADATA,
                Caffeine.newBuilder()
                        .maximumSize(10)
                        .recordStats()
                        .build()
        );

        var manager = new SimpleCacheManager();
        manager.setCaches(List.of(prompts, metadata));
        return manager;
    }
}
