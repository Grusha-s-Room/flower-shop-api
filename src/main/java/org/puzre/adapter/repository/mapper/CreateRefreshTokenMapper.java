package org.puzre.adapter.repository.mapper;

import jakarta.inject.Singleton;
import org.puzre.adapter.repository.entity.RefreshTokenEntity;
import org.puzre.core.domain.CreateRefreshToken;
import org.puzre.core.port.mapper.IRepositoryEntityMapper;

@Singleton
public class CreateRefreshTokenMapper implements IRepositoryEntityMapper<CreateRefreshToken, RefreshTokenEntity> {
    @Override
    public RefreshTokenEntity toEntity(CreateRefreshToken domain) {
        return RefreshTokenEntity.builder()
                .refreshToken(domain.getRefreshToken())
                .expiresAt(domain.getExpiresAt())
                .build();
    }
}
