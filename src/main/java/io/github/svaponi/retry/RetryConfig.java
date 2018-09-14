package io.github.svaponi.retry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableRetry
@Configuration
public class RetryConfig {

    @Bean
    public RetryService retryService() {
        return new RetryService();
    }
}
