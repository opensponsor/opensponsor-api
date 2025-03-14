package com.opensponsor.payload;


import com.opensponsor.entitys.CountryCode;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class LoginBody {
    @Schema(required = true, description = "countryCode")
    public CountryCode countryCode;

    @Size(min = 2, max = 32)
    @Schema(required = true, description = "phoneNumber")
    public String phoneNumber;

    @Size(min = 2, max = 32)
    @Schema(required = true, description = "email")
    public String email;

    @Size(min = 6, max = 64)
    @Schema(required = true, description = "password")
    public String password;

    @Size(min = 4, max = 4)
    @Schema(required = true, description = "code")
    public String code;
}
