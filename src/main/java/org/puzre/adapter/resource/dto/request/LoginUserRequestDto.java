package org.puzre.adapter.resource.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@NotNull
public class LoginUserRequestDto {
    @NotNull @NotBlank @NotEmpty
    private String email;
    @NotNull @NotBlank @NotEmpty
    private String password;
}
