package org.puzre.adapter.auth;

import jakarta.enterprise.context.ApplicationScoped;
import org.puzre.adapter.configuration.CookieConfiguration;
import org.puzre.core.domain.Cookie;
import org.puzre.core.port.auth.ICookieProvider;

@ApplicationScoped
public class CookieProvider implements ICookieProvider {

    private final CookieConfiguration cookieConfiguration;

    public CookieProvider(CookieConfiguration cookieConfiguration){
        this.cookieConfiguration = cookieConfiguration;
    }

    @Override
    public Cookie createCookie(String name, String token, long maxAge) {
        return Cookie.builder()
                .name(name)
                .value(token)
                .maxAge(Integer.parseInt(String.valueOf(maxAge)))
                .path(cookieConfiguration.path())
                .domain(cookieConfiguration.domain())
                .secure(cookieConfiguration.secure())
                .httpOnly(cookieConfiguration.httpOnly())
                .build();
    }

}
