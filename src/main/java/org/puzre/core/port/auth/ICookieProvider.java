package org.puzre.core.port.auth;

import org.puzre.core.domain.Cookie;

public interface ICookieProvider {

    Cookie createCookie(String name, String token, long maxAge);

}
