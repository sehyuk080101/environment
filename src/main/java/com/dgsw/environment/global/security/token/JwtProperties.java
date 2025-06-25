package com.dgsw.environment.global.security.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Getter
@AllArgsConstructor
public class JwtProperties {
    private final String secret;
    private final DurationProperty duration;

    public record DurationProperty(long access, long refresh) {
    }
}
