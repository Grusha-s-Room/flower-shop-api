package org.puzre.adapter.auth;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.jwt.Claims;
import org.jose4j.jwt.JwtClaims;
import org.puzre.adapter.configuration.JwtConfiguration;
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

    public JwtTokenProvider(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    @Override
    public Token generateToken(LoginUser loginUser) {

        try {

            JwtClaims jwtClaims = new JwtClaims();

            jwtClaims.setIssuer(jwtConfiguration.issuer());
            jwtClaims.setJwtId(UUID.randomUUID().toString());
            jwtClaims.setAudience(jwtConfiguration.audience());
            jwtClaims.setSubject(loginUser.getId().toString());
            jwtClaims.setClaim(Claims.groups.name(), Collections.singletonList(jwtConfiguration.userRole()));
            jwtClaims.setExpirationTimeMinutesInTheFuture(jwtConfiguration.expirationTimeMinutes());

            String token = JwtTokenUtils.generateTokenString(jwtClaims, jwtConfiguration.privateKeyPath());

            JwtClaims jwtProperties = JwtTokenUtils.getTokenProperties(token,
                    jwtConfiguration.publicKeyPath(),
                    jwtConfiguration.issuer(),
                    loginUser.getId(),
                    jwtConfiguration.audience());

            return Token.builder()
                    .token(token)
                    .issuedAt(jwtProperties.getIssuedAt().getValue())
                    .expiresIn(TimeUnit.MINUTES.toMillis(jwtConfiguration.expirationTimeMinutes()))
                    .cookeName(jwtConfiguration.cookieName())
                    .build();

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

}
