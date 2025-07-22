package org.puzre.adapter.auth;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;
import org.jose4j.jwt.JwtClaims;
import org.puzre.core.domain.LoginUser;
import org.puzre.core.domain.Token;
import org.puzre.core.exception.BadRequestException;
import org.puzre.core.port.auth.ITokenProvider;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class JwtTokenProvider implements ITokenProvider {

    @ConfigProperty(name = "app.jwt.user-role")
    String userRole;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @ConfigProperty(name = "mp.jwt.verify.audiences")
    String audience;

    @ConfigProperty(name = "mp.jwt.verify.publickey.location")
    String publicKeyPath;

    @ConfigProperty(name = "app.jwt.private-key-path")
    String privateKeyPath;

    @ConfigProperty(name = "app.jwt.expiration-time-minutes")
    int expirationTimeMinutes;

    @Override
    public Token generateToken(LoginUser loginUser) {

        try {

            JwtClaims jwtClaims = new JwtClaims();

            jwtClaims.setIssuer(issuer);
            jwtClaims.setJwtId(UUID.randomUUID().toString());
            jwtClaims.setAudience(audience);
            jwtClaims.setSubject(loginUser.getId().toString());
            jwtClaims.setClaim(Claims.groups.name(), Collections.singletonList(userRole));
            jwtClaims.setExpirationTimeMinutesInTheFuture(expirationTimeMinutes);

            String token = JwtTokenUtils.generateTokenString(jwtClaims, privateKeyPath);

            JwtClaims jwtProperties = JwtTokenUtils.getTokenProperties(token, publicKeyPath, issuer, loginUser.getId(), audience);

            return Token.builder()
                    .token(token)
                    .issuedAt(jwtProperties.getIssuedAt().getValue())
                    .expiresIn(TimeUnit.MINUTES.toMillis(expirationTimeMinutes))
                    .build();

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

}
