package org.puzre.adapter.resource.mapper;

import jakarta.inject.Singleton;
import org.puzre.adapter.resource.dto.request.SignUpUserRequestDto;
import org.puzre.core.domain.User;
import org.puzre.core.port.mapper.IRequestMapper;

@Singleton
public class SignUpUserDtoMapper implements IRequestMapper<User, SignUpUserRequestDto> {

    @Override
    public User toDomain(SignUpUserRequestDto requestDto) {
        return User.builder()
                .name(requestDto.getName())
                .surname(requestDto.getSurname())
                .phone(requestDto.getPhone())
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .build();
    }

}
