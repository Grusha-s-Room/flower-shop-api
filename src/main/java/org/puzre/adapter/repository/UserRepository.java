package org.puzre.adapter.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.puzre.adapter.repository.entity.UserEntity;
import org.puzre.core.domain.User;
import org.puzre.core.exception.ConflictException;
import org.puzre.core.port.mapper.IRepositoryMapper;
import org.puzre.core.port.respository.IUserRepository;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity>, IUserRepository {

    private final IRepositoryMapper<User, UserEntity> iRepositoryMapper;

    public UserRepository(IRepositoryMapper<User, UserEntity> iRepositoryMapper) {
        this.iRepositoryMapper = iRepositoryMapper;
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

        UserEntity userEntity = iRepositoryMapper.toEntity(user);
        this.persist(userEntity);

    }

}
