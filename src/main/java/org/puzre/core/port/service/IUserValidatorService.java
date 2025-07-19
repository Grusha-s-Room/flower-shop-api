package org.puzre.core.port.service;

public interface IUserValidatorService {

    void validateName(String name);
    void validateSurname(String surname);
    void validateSignUpPassword(String password);
    void validateEmail(String email);
    void validatePhone(String phone);
    void validateLoginPassword(String password);

}
