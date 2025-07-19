package org.puzre.core.port.auth;

import org.puzre.core.domain.LoginUser;
import org.puzre.core.domain.Token;

public interface ITokenProvider {

    Token generateToken(LoginUser loginUser);

}
