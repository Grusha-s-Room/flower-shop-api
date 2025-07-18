package org.puzre.adapter.resource.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@NotNull
public class SignUpUserRequestDto {

    @NotNull @NotBlank @NotEmpty
    private String name;
    @NotNull @NotBlank @NotEmpty
    private String surname;
    @NotNull @NotBlank @NotEmpty
    private String phone;
    @NotNull @NotBlank @NotEmpty @Email
    private String email;
    @NotNull @NotBlank @NotEmpty
    private String password;

}
