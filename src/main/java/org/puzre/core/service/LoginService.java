package org.puzre.core.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.puzre.core.domain.Cookie;
import org.puzre.core.domain.LoginUser;
import org.puzre.core.domain.Token;
import org.puzre.core.port.auth.ICookieProvider;
import org.puzre.core.port.auth.ITokenProvider;
import org.puzre.core.port.respository.IUserRepository;
import org.puzre.core.port.service.IEncoderService;
import org.puzre.core.port.service.ILoginService;
import org.puzre.core.port.service.IUserValidatorService;

@ApplicationScoped
public class LoginService implements ILoginService {

    private final IUserValidatorService iUserValidatorService;
    private final IEncoderService iEncoderService;

    private final ITokenProvider iTokenProvider;
    private final ICookieProvider iCookieProvider;

    private final IUserRepository iUserRepository;

    public LoginService(
            IUserValidatorService iUserValidatorService,
            IEncoderService iEncoderService,
            ITokenProvider iTokenProvider,
            ICookieProvider iCookieProvider,
            IUserRepository iUserRepository
    ){
        this.iUserValidatorService = iUserValidatorService;
        this.iUserRepository = iUserRepository;
        this.iTokenProvider = iTokenProvider;
        this.iCookieProvider = iCookieProvider;
        this.iEncoderService = iEncoderService;
    }

    @Override
    public LoginUser login(LoginUser loginUser) {

        iUserValidatorService.validateEmail(loginUser.getEmail());
        iUserValidatorService.validateLoginPassword(loginUser.getPassword());

        iUserRepository.verifyUserExistByEmail(loginUser.getEmail());

        LoginUser userAccount = iUserRepository.findLoginUserByEmail(loginUser.getEmail());

        iEncoderService.passwordMatches(loginUser.getPassword(), userAccount.getPassword());

        Token token = iTokenProvider.generateToken(userAccount);

        Cookie cookie = iCookieProvider.createCookie(token.getCookeName(), token.getToken(), token.getExpiresIn());

        userAccount.setCookie(cookie);

        return userAccount;
    }
}
