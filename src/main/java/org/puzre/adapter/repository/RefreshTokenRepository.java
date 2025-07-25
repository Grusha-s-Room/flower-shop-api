package org.puzre.adapter.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.puzre.adapter.repository.entity.RefreshTokenEntity;
import org.puzre.core.port.respository.IRefreshTokenRepository;

@ApplicationScoped
public class RefreshTokenRepository implements PanacheRepository<RefreshTokenEntity>, IRefreshTokenRepository {



}
