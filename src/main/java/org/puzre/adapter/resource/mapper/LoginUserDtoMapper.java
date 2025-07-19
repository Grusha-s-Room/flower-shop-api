package org.puzre.adapter.resource.mapper;

import jakarta.inject.Singleton;
import org.puzre.adapter.resource.dto.request.LoginUserRequestDto;
import org.puzre.adapter.resource.dto.response.LoginUserResponseDto;
import org.puzre.adapter.resource.dto.response.TokenResponseDto;
import org.puzre.core.domain.LoginUser;
import org.puzre.core.domain.Token;
import org.puzre.core.port.mapper.IResourceMapper;
import org.puzre.core.port.mapper.IResponseMapper;

@Singleton
public class LoginUserDtoMapper implements IResourceMapper<LoginUser, LoginUserRequestDto, LoginUserResponseDto> {

    private final IResponseMapper<Token, TokenResponseDto> iTokenDtoMapper;

    public LoginUserDtoMapper(
            IResponseMapper<Token, TokenResponseDto> iTokenDtoMapper
    ) {
        this.iTokenDtoMapper = iTokenDtoMapper;
    }

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
                .auth(iTokenDtoMapper.toResponseDto(domain.getAuth()))
                .build();
    }

}
