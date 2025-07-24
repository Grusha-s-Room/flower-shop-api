package org.puzre.adapter.configuration;

import io.smallrye.config.ConfigMapping;
import jakarta.enterprise.context.ApplicationScoped;

@ConfigMapping(prefix = "app.cookie")
@ApplicationScoped
public interface CookieConfiguration {

    String name();
    String path();
    String domain();
    boolean secure();
    boolean httpOnly();

}
