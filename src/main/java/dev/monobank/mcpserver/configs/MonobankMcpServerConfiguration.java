package dev.monobank.mcpserver.configs;

import dev.monobank.mcpserver.service.MonobankToolService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class MonobankMcpServerConfiguration {

    @Value("${monobank.api-token}")
    private String monobankApiToken;

    @Bean
    public RestClient monobankRestClient(RestClient.Builder builder) {
        return builder.baseUrl("https://api.monobank.ua/")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("X-Token", monobankApiToken)
                .build();
    }

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public ToolCallbackProvider toolCallbackProvider(MonobankToolService monobankToolService) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(monobankToolService)
                .build();
    }
}
