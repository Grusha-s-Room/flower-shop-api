package org.puzre.core.port.auth;

import org.puzre.core.domain.Token;
import org.puzre.core.domain.User;

public interface ITokenProvider {

    Token generateMainToken(User user);
    Token generateRefreshToken(User user);

}
