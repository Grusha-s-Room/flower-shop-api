package org.puzre.adapter.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RefreshTokenEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "refresh_token", nullable = false, unique = true, length = 1000)
    private String refreshToken;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Builder.Default
    @Column(name = "valid", nullable = false)
    private boolean valid = true;

    @Column(name = "rotated_from")
    private Long rotatedFrom;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public void update(RefreshTokenEntity refreshTokenEntity) {
        this.refreshToken = (refreshTokenEntity.getRefreshToken() != null) ? refreshTokenEntity.getRefreshToken() : this.refreshToken;
        this.expiresAt = (refreshTokenEntity.getExpiresAt() != null) ? refreshTokenEntity.getExpiresAt() : this.expiresAt;
        this.valid = refreshTokenEntity.isValid();
        this.rotatedFrom = (refreshTokenEntity.getRotatedFrom() != null) ? refreshTokenEntity.getRotatedFrom() : this.rotatedFrom;
    }

}
