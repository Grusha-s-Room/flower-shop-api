package org.puzre.core.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.puzre.core.domain.*;
import org.puzre.core.port.auth.ICookieProvider;
import org.puzre.core.port.auth.ITokenProvider;
import org.puzre.core.port.respository.IRefreshTokenRepository;
import org.puzre.core.port.respository.IUserRepository;
import org.puzre.core.port.service.IEncoderService;
import org.puzre.core.port.service.ILoginService;
import org.puzre.core.port.service.IUserValidatorService;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class LoginService implements ILoginService {

    private final IUserValidatorService iUserValidatorService;
    private final IEncoderService iEncoderService;

    private final ITokenProvider iTokenProvider;
    private final ICookieProvider iCookieProvider;

    private final IUserRepository iUserRepository;
    private final IRefreshTokenRepository iRefreshTokenRepository;

    public LoginService(
            IUserValidatorService iUserValidatorService,
            IEncoderService iEncoderService,
            ITokenProvider iTokenProvider,
            ICookieProvider iCookieProvider,
            IUserRepository iUserRepository,
            IRefreshTokenRepository iRefreshTokenRepository
    ){
        this.iUserValidatorService = iUserValidatorService;
        this.iUserRepository = iUserRepository;
        this.iTokenProvider = iTokenProvider;
        this.iCookieProvider = iCookieProvider;
        this.iEncoderService = iEncoderService;
        this.iRefreshTokenRepository = iRefreshTokenRepository;
    }

    @Override
    public LoginUser login(LoginUser loginUser) {

        iUserValidatorService.validateEmail(loginUser.getEmail());
        iUserValidatorService.validateLoginPassword(loginUser.getPassword());

        iUserRepository.verifyUserExistByEmail(loginUser.getEmail());

        User user = iUserRepository.findUserByEmail(loginUser.getEmail());

        iEncoderService.passwordMatches(loginUser.getPassword(), user.getPassword());

        Token mainToken = iTokenProvider.generateMainToken(user);
        Token refreshToken = iTokenProvider.generateRefreshToken(user);

        List<RefreshToken> validRefreshTokens = iRefreshTokenRepository.getAllValidRefreshTokenByUserId(user.getId());

        if (!validRefreshTokens.isEmpty()) {
            validRefreshTokens.forEach(RefreshToken::invalidate);
            System.out.println(validRefreshTokens);
            iRefreshTokenRepository.saveAll(validRefreshTokens);
        }

        CreateRefreshToken domainRefreshToken = CreateRefreshToken.builder()
                .refreshToken(refreshToken.getToken())
                .expiresAt(Instant.now().plusMillis(TimeUnit.MINUTES.toMillis(refreshToken.getExpiresIn())))
                .build();

        iRefreshTokenRepository.saveWithUser(domainRefreshToken, user.getId());

        Cookie mainTokenCookie = iCookieProvider.createCookie(mainToken.getCookieName(), mainToken.getToken(), mainToken.getExpiresIn(), mainToken.getCookiePath());
        Cookie refreshTokenCookie = iCookieProvider.createCookie(refreshToken.getCookieName(), refreshToken.getToken(), refreshToken.getExpiresIn(), refreshToken.getCookiePath());

        return LoginUser.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .mainTokenCookie(mainTokenCookie)
                .refreshTokenCookie(refreshTokenCookie)
                .build();
    }
}
