package org.puzre.core.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.puzre.core.exception.BadRequestException;
import org.puzre.core.port.service.IUserValidatorService;

import java.util.regex.Pattern;

@ApplicationScoped
public class UserValidatorService implements IUserValidatorService {

    @Override
    public void validateName(String name) {
        if (name.isBlank())
            throw new BadRequestException("name must not be empty");
        if (name.length() > 50)
            throw new BadRequestException("names is too long");
    }

    @Override
    public void validateSurname(String surname) {
        if (surname.isBlank())
            throw new BadRequestException("surname must not be empty");
        if (surname.length() > 50)
            throw new BadRequestException("surname is too long");
    }

    @Override
    public void validateSignUpPassword(String password) {
        if (password.length() < 8)
            throw new BadRequestException("password must have at least 8 characters");
        if (password.length() > 20)
            throw new BadRequestException("password is too long");
    }

    @Override
    public void validateEmail(String email) {
        if (email.length() > 50)
            throw new BadRequestException("email is too long");
        if (!Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$").matcher(email).matches())
            throw new BadRequestException("invalid email");
    }

    @Override
    public void validatePhone(String phone) {
        if (phone.length() != 13)
            throw new BadRequestException("invalid phone");
    }

    @Override
    public void validateLoginPassword(String password) {
        if (password.isBlank() || password.length() > 20)
            throw new BadRequestException("password is too long");
    }

}
