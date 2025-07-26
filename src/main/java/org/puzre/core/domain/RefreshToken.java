package org.puzre.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RefreshToken {

    private Long id;
    private String refreshToken;
    private Instant expiresAt;
    private boolean valid;
    private Long rotatedFrom;

    public void invalidate() {
        this.valid = false;
    }

}
