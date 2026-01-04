package dev.monobank.mcpserver.configs;

import com.joestelmach.natty.Parser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class MonobankMcpServerConfiguration {

    @Value("${monobank.api-token}")
    private String monobankApiToken;

    @Bean
    public RestClient monobankRestClient(final RestClient.Builder builder) {
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
    public Parser parser() {
        return new Parser();
    }
}
