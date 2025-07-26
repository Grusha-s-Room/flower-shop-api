package org.puzre.adapter.repository.mapper;

import jakarta.inject.Singleton;
import org.puzre.adapter.repository.entity.UserEntity;
import org.puzre.core.domain.User;
import org.puzre.core.port.mapper.IRepositoryMapper;

@Singleton
public class UserMapper implements IRepositoryMapper<User, UserEntity> {

    @Override
    public User toDomain(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .build();
    }

    @Override
    public UserEntity toEntity(User domain) {
        return UserEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .surname(domain.getSurname())
                .phone(domain.getPhone())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .build();
    }
}
