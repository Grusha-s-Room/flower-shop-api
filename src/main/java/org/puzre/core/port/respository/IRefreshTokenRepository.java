package org.puzre.core.port.respository;

import org.puzre.core.domain.CreateRefreshToken;
import org.puzre.core.domain.RefreshToken;

import java.util.List;

public interface IRefreshTokenRepository {

    List<RefreshToken> getAllValidRefreshTokenByUserId(Long userId);
    void saveWithUser(CreateRefreshToken createRefreshToken, Long userId);
    void saveAll(List<RefreshToken> refreshTokens);

}
