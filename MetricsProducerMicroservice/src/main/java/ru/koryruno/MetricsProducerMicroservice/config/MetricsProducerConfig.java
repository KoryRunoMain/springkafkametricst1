package ru.koryruno.MetricsProducerMicroservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for setting up beans related to the metrics producer.
 * <p>
 * This class provides the configuration for creating a {@link RestTemplate} bean,
 * which can be used to perform HTTP requests within the application.
 * </p>
 */
@Configuration
public class MetricsProducerConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
