package org.puzre.core.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import org.puzre.core.exception.UnauthorizedException;
import org.puzre.core.port.service.IEncoderService;

@ApplicationScoped
public class EncoderService implements IEncoderService {

    @Override
    public String hash(String string) {
        return BcryptUtil.bcryptHash(string, 4);
    }

    @Override
    public boolean matches(String rawString, String hashedString) {
        return BcryptUtil.matches(rawString, hashedString);
    }

    @Override
    public void passwordMatches(String rawPassword, String hashedPassword) {
        if (!BcryptUtil.matches(rawPassword, hashedPassword))
            throw new UnauthorizedException("invalid password");
    }

}
