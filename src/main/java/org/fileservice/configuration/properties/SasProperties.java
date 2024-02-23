package org.fileservice.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SasProperties.SasAccessProperties.class)
public class SasProperties {

    @ConfigurationProperties(prefix = "cloud.blob.sas")
    public record SasAccessProperties(long accessDuration) {
    }
}