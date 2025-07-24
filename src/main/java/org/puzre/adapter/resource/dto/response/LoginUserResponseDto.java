package org.puzre.adapter.resource.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserResponseDto {
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String email;
}
