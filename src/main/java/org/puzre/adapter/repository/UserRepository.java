package org.puzre.adapter.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.puzre.adapter.repository.entity.UserEntity;
import org.puzre.core.domain.User;
import org.puzre.core.exception.ConflictException;
import org.puzre.core.exception.NotFoundException;
import org.puzre.core.port.mapper.IRepositoryMapper;
import org.puzre.core.port.respository.IUserRepository;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity>, IUserRepository {

    private final IRepositoryMapper<User, UserEntity> iUserMapper;

    public UserRepository(IRepositoryMapper<User, UserEntity> iUserMapper) {
        this.iUserMapper = iUserMapper;
    }

    @Override
    public void verifyEmail(String email) {
        Optional<UserEntity> optionalUserEntity = this.find("email = ?1", email).stream().findAny();
        if (optionalUserEntity.isPresent())
            throw new ConflictException("email is already registered");
    }

    @Override
    public void verifyPhone(String phone) {
        Optional<UserEntity> optionalUserEntity = this.find("phone = ?1", phone).stream().findAny();
        if (optionalUserEntity.isPresent())
            throw new ConflictException("phone is already registered");
    }

    @Override
    @Transactional
    public void save(User user) {
        UserEntity userEntity = iUserMapper.toEntity(user);
        this.persist(userEntity);
    }

    @Override
    public void verifyUserExistByEmail(String email) {
        Optional<UserEntity> optionalUserEntity = this.find("email = ?1", email).stream().findFirst();
        if (optionalUserEntity.isEmpty())
            throw new NotFoundException("this email does not have an account associated");
    }

    @Override
    public User findUserByEmail(String email) {
        Optional<UserEntity> optionalUserEntity = this.find("email = ?1", email).stream().findFirst();
        if (optionalUserEntity.isEmpty())
            throw new NotFoundException("user not found");
        return iUserMapper.toDomain(optionalUserEntity.get());
    }

}
