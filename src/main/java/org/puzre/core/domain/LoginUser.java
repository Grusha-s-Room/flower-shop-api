package org.puzre.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phone;
    private Cookie mainTokenCookie;
    private Cookie refreshTokenCookie;

}
