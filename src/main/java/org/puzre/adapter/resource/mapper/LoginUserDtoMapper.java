package org.puzre.adapter.resource.mapper;

import jakarta.inject.Singleton;
import org.puzre.adapter.resource.dto.request.LoginUserRequestDto;
import org.puzre.adapter.resource.dto.response.LoginUserResponseDto;
import org.puzre.core.domain.LoginUser;
import org.puzre.core.port.mapper.IResourceMapper;

@Singleton
public class LoginUserDtoMapper implements IResourceMapper<LoginUser, LoginUserRequestDto, LoginUserResponseDto> {

    @Override
    public LoginUser toDomain(LoginUserRequestDto requestDto) {
        return LoginUser.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .build();
    }

    @Override
    public LoginUserResponseDto toResponseDto(LoginUser domain) {
        return LoginUserResponseDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .surname(domain.getSurname())
                .phone(domain.getPhone())
                .email(domain.getEmail())
                .jwt(domain.getJwt())
                .build();
    }

}
