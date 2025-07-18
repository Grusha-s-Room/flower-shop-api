package org.puzre.core.port.service;

public interface IUserValidatorService {

    void validateName(String name);
    void validateSurname(String surname);
    void validatePassword(String password);
    void validateEmail(String email);
    void validatePhone(String phone);

}
