package org.puzre.core.port.respository;

import org.puzre.core.domain.LoginUser;
import org.puzre.core.domain.User;

public interface IUserRepository {

    void verifyEmail(String email);
    void verifyPhone(String phone);
    void save(User user);
    void verifyUserExistByEmail(String email);
    LoginUser findLoginUserByEmail(String email);

}
