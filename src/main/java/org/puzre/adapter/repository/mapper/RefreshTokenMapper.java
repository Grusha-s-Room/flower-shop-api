package org.puzre.adapter.repository.mapper;

import jakarta.inject.Singleton;
import org.puzre.adapter.repository.entity.RefreshTokenEntity;
import org.puzre.core.domain.RefreshToken;
import org.puzre.core.port.mapper.IRepositoryMapper;

@Singleton
public class RefreshTokenMapper implements IRepositoryMapper<RefreshToken, RefreshTokenEntity> {

    @Override
    public RefreshTokenEntity toEntity(RefreshToken domain) {
        return RefreshTokenEntity.builder()
                .id(domain.getId())
                .refreshToken(domain.getRefreshToken())
                .expiresAt(domain.getExpiresAt())
                .refreshToken(domain.getRefreshToken())
                .valid(domain.isValid())
                .rotatedFrom(domain.getRotatedFrom())
                .build();
    }


    @Override
    public RefreshToken toDomain(RefreshTokenEntity entity) {
        return RefreshToken.builder()
                .id(entity.getId())
                .refreshToken(entity.getRefreshToken())
                .expiresAt(entity.getExpiresAt())
                .valid(entity.isValid())
                .rotatedFrom(entity.getRotatedFrom())
                .build();
    }
}
