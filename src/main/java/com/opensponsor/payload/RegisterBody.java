package com.opensponsor.payload;

import com.opensponsor.entitys.CountryCode;
import com.opensponsor.enums.E_SEX;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.Comment;

public class RegisterBody {
    @Comment("user name")
    @Size(min = 2, max = 32)
    @NotBlank
    @NotEmpty
    @NotNull
    @Schema(required = true, description = "user name")
    public String username;

    @Comment("url slug")
    @NotBlank
    @NotEmpty
    @NotNull
    @Schema(required = true, description = "url slug")
    public String slug;

    @Comment("phone number")
    @NotBlank
    @NotEmpty
    @NotNull
    @Schema(required = true, description = "phone number")
    public String phoneNumber;

    @Comment("code")
    @NotBlank
    @NotEmpty
    @NotNull
    @Schema(required = true, description = "code")
    public String code;

    @Comment("country code")
    @NotNull
    @Schema(required = true, description = "country code")
    public CountryCode countryCode;

    @Comment("User Sex")
    @Enumerated(EnumType.STRING)
    @Schema(required = true, description = "sex")
    public E_SEX sex;

    @Comment("password")
    @Size(min = 8, max = 32)
    @NotBlank
    @NotEmpty
    @NotNull
    @Schema(required = true, description = "password")
    public String password;
}
