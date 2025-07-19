package org.puzre.adapter.repository.mapper;

import jakarta.inject.Singleton;
import org.puzre.adapter.repository.entity.UserEntity;
import org.puzre.core.domain.User;
import org.puzre.core.port.mapper.IRepositoryEntityMapper;

@Singleton
public class SignUpUserMapper implements IRepositoryEntityMapper<User, UserEntity> {

    @Override
    public UserEntity toEntity(User domain) {
        return UserEntity
                .builder()
                .name(domain.getName())
                .surname(domain.getSurname())
                .email(domain.getEmail())
                .phone(domain.getPhone())
                .password(domain.getPassword())
                .build();
    }

}
