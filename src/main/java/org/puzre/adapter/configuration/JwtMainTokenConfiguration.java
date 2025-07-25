package org.puzre.adapter.configuration;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "app.jwt.main")
public interface JwtMainTokenConfiguration {

    long expirationTimeMinutes();
    String cookieName();
    String cookiePath();

}
