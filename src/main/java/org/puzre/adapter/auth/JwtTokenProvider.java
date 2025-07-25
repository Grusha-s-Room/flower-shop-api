package org.puzre.adapter.auth;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.jwt.Claims;
import org.jose4j.jwt.JwtClaims;
import org.puzre.adapter.configuration.JwtConfiguration;
import org.puzre.adapter.configuration.JwtMainTokenConfiguration;
import org.puzre.adapter.configuration.JwtRefreshTokenConfiguration;
import org.puzre.core.domain.LoginUser;
import org.puzre.core.domain.Token;
import org.puzre.core.exception.BadRequestException;
import org.puzre.core.port.auth.ITokenProvider;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class JwtTokenProvider implements ITokenProvider {

    private final JwtConfiguration jwtConfiguration;
    private final JwtMainTokenConfiguration jwtMainTokenConfiguration;
    private final JwtRefreshTokenConfiguration jwtRefreshTokenConfiguration;

    public JwtTokenProvider(JwtConfiguration jwtConfiguration,
                            JwtMainTokenConfiguration jwtMainTokenConfiguration,
                            JwtRefreshTokenConfiguration jwtRefreshTokenConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
        this.jwtMainTokenConfiguration = jwtMainTokenConfiguration;
        this.jwtRefreshTokenConfiguration = jwtRefreshTokenConfiguration;
    }

    @Override
    public Token generateMainToken(LoginUser loginUser) {

        try {

            JwtClaims jwtClaims = new JwtClaims();

            jwtClaims.setIssuer(jwtConfiguration.issuer());
            jwtClaims.setJwtId(UUID.randomUUID().toString());
            jwtClaims.setAudience(jwtConfiguration.audience());
            jwtClaims.setSubject(loginUser.getId().toString());
            jwtClaims.setClaim(Claims.groups.name(), Collections.singletonList(jwtConfiguration.userRole()));
            jwtClaims.setExpirationTimeMinutesInTheFuture(jwtMainTokenConfiguration.expirationTimeMinutes());

            String token = JwtTokenUtils.generateTokenString(jwtClaims, jwtConfiguration.privateKeyPath());

            return Token.builder()
                    .token(token)
                    .expiresIn(TimeUnit.MINUTES.toMillis(jwtMainTokenConfiguration.expirationTimeMinutes()))
                    .cookieName(jwtMainTokenConfiguration.cookieName())
                    .cookiePath(jwtMainTokenConfiguration.cookiePath())
                    .build();

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public Token generateRefreshToken(LoginUser loginUser) {



        return null;
    }

}
