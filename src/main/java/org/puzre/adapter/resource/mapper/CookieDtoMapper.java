package org.puzre.adapter.resource.mapper;

import jakarta.inject.Singleton;
import jakarta.ws.rs.core.NewCookie;
import org.puzre.core.domain.Cookie;
import org.puzre.core.port.mapper.IResponseMapper;

@Singleton
public class CookieDtoMapper implements IResponseMapper<Cookie, NewCookie> {
    @Override
    public NewCookie toResponseDto(Cookie domain) {
        return new NewCookie.Builder(domain.getName())
                .value(domain.getValue())
                .path(domain.getPath())
                .domain(domain.getDomain())
                .maxAge(domain.getMaxAge())
                .secure(domain.isSecure())
                .httpOnly(domain.isHttpOnly())
                .build();
    }
}
