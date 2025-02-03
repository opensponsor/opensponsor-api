package com.opensponsor.payload;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class LoginBody {
    @Size(min = 2, max = 32)
    @NotBlank()
    @NotNull()
    @NotEmpty()
    @Schema(required = true, description = "account")
    public String account;

    @Size(min = 6, max = 64)
    @NotBlank()
    @NotNull()
    @NotEmpty()
    @Schema(required = true, description = "password")
    public String password;
}
