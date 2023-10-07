package it.pkg.config;

import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@EnableConfigurationProperties(ApplicationProperties.class)
public class ApplicationConfiguration {
    private final ApplicationProperties properties;
}
