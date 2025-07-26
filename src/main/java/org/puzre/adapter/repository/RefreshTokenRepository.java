package org.puzre.adapter.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.puzre.adapter.repository.entity.RefreshTokenEntity;
import org.puzre.adapter.repository.entity.UserEntity;
import org.puzre.core.domain.CreateRefreshToken;
import org.puzre.core.domain.RefreshToken;
import org.puzre.core.port.mapper.IRepositoryEntityMapper;
import org.puzre.core.port.mapper.IRepositoryMapper;
import org.puzre.core.port.respository.IRefreshTokenRepository;
import org.puzre.core.port.respository.IUserRepository;

import java.util.List;

@ApplicationScoped
public class RefreshTokenRepository implements PanacheRepository<RefreshTokenEntity>, IRefreshTokenRepository {

    public final IRepositoryMapper<RefreshToken, RefreshTokenEntity> iRefreshTokenEntityMapper;
    public final IRepositoryEntityMapper<CreateRefreshToken, RefreshTokenEntity> iCreateRefreshTokenMapper;

    public final IUserRepository iUserRepository;

    public final EntityManager entityManager;

    public RefreshTokenRepository(
            IRepositoryMapper<RefreshToken, RefreshTokenEntity> iRefreshTokenEntityMapper,
            IRepositoryEntityMapper<CreateRefreshToken, RefreshTokenEntity> iCreateRefreshTokenMapper,
            IUserRepository iUserRepository,
            EntityManager entityManager
    ) {
      this.iRefreshTokenEntityMapper = iRefreshTokenEntityMapper;
      this.iCreateRefreshTokenMapper = iCreateRefreshTokenMapper;
      this.iUserRepository = iUserRepository;
      this.entityManager = entityManager;
    }

    @Override
    public List<RefreshToken> getAllValidRefreshTokenByUserId(Long userId) {
        return this.find("user.id = ?1 AND valid = true", userId).stream()
                .map(iRefreshTokenEntityMapper::toDomain).toList();
    }

    @Override
    @Transactional
    public void saveWithUser(CreateRefreshToken createRefreshToken, Long userId) {
        RefreshTokenEntity refreshTokenEntity = iCreateRefreshTokenMapper.toEntity(createRefreshToken);
        UserEntity userEntity = entityManager.getReference(UserEntity.class, userId);
        refreshTokenEntity.setUser(userEntity);
        this.persist(refreshTokenEntity);
    }

    @Override
    @Transactional
    public void saveAll(List<RefreshToken> refreshTokens) {
        this.persist(refreshTokens.stream().map(it -> {
            RefreshTokenEntity updatedRefreshTokenEntity = iRefreshTokenEntityMapper.toEntity(it);
            RefreshTokenEntity refreshTokenEntity = entityManager.getReference(RefreshTokenEntity.class, it.getId());
            refreshTokenEntity.update(updatedRefreshTokenEntity);
            return refreshTokenEntity;
        }));
    }


}
