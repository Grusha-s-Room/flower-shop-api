package org.puzre.core.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.puzre.core.domain.User;
import org.puzre.core.port.respository.IUserRepository;
import org.puzre.core.port.service.IEncoderService;
import org.puzre.core.port.service.ISignUpService;
import org.puzre.core.port.service.IUserValidatorService;

@ApplicationScoped
public class SignUpService implements ISignUpService {

    private final IUserValidatorService iUserValidatorService;
    private final IEncoderService iEncoderService;

    private final IUserRepository iUserRepository;

    public SignUpService(IUserValidatorService iUserValidatorService,
                         IEncoderService iEncoderService,
                         IUserRepository iUserRepository) {
        this.iUserValidatorService = iUserValidatorService;
        this.iEncoderService = iEncoderService;
        this.iUserRepository = iUserRepository;
    }

    @Override
    public void signUp(User user) {

        iUserValidatorService.validateName(user.getName());
        iUserValidatorService.validateSurname(user.getSurname());
        iUserValidatorService.validateEmail(user.getEmail());
        iUserValidatorService.validatePhone(user.getPhone());
        iUserValidatorService.validatePassword(user.getPassword());

        System.out.println("pass validations");

        iUserRepository.verifyEmail(user.getEmail());
        iUserRepository.verifyEmail(user.getPhone());

        System.out.println("pass db validations");

        String hashedPassword = iEncoderService.hash(user.getPassword());
        user.setPassword(hashedPassword);

        System.out.println("pass hash password");

        iUserRepository.save(user);

    }

}
