package org.puzre.adapter.resource.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDto {

    private String token;
    private long issuedAt;
    private long expiresIn;

}
