package com.opensponsor.payload;


import com.opensponsor.entitys.CountryCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.Comment;

public class LoginForSMSBody {
    @Size(min = 11, max = 11)
    @NotBlank()
    @NotNull()
    @NotEmpty()
    @Schema(required = true, description = "phone number")
    public String phoneNumber;

    @Comment("country code")
    @Schema(required = true, description = "country code")
    @NotNull
    public CountryCode countryCode;

    @Size(min = 4, max = 4)
    @NotBlank()
    @NotNull()
    @NotEmpty()
    @Schema(required = true, description = "code")
    public String code;
}
