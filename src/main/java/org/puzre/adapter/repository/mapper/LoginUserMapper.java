package org.puzre.adapter.repository.mapper;

import jakarta.inject.Singleton;
import org.puzre.adapter.repository.entity.UserEntity;
import org.puzre.core.domain.LoginUser;
import org.puzre.core.port.mapper.IRepositoryDomainMapper;

@Singleton
public class LoginUserMapper implements IRepositoryDomainMapper<LoginUser, UserEntity> {
    @Override
    public LoginUser toDomain(UserEntity entity) {
        return LoginUser.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .build();
    }
}
