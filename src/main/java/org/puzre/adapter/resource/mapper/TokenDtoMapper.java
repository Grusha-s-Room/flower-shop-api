package org.puzre.adapter.resource.mapper;

import jakarta.inject.Singleton;
import org.puzre.adapter.resource.dto.response.TokenResponseDto;
import org.puzre.core.domain.Token;
import org.puzre.core.port.mapper.IResponseMapper;

@Singleton
public class TokenDtoMapper implements IResponseMapper<Token, TokenResponseDto> {
    @Override
    public TokenResponseDto toResponseDto(Token domain) {
        return TokenResponseDto.builder()
                .token(domain.getToken())
                .issuedAt(domain.getIssuedAt())
                .expiresIn(domain.getExpiresIn())
                .build();
    }
}
