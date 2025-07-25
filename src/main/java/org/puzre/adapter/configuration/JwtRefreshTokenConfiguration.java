package org.puzre.adapter.configuration;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "app.jwt.refresh")
public interface JwtRefreshTokenConfiguration {

    long expirationTimeMinutes();
    String cookieName();
    String cookiePath();

}
