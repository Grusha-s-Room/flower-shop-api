package org.puzre.adapter.configuration;

import io.smallrye.config.ConfigMapping;
import jakarta.enterprise.context.ApplicationScoped;

@ConfigMapping(prefix = "app.jwt")
@ApplicationScoped
public interface JwtConfiguration {

    String publicKeyPath();
    String issuer();
    String audience();
    String userRole();
    String privateKeyPath();
    long expirationTimeMinutes();

}
