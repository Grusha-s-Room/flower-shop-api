package org.puzre.core.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
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

}
