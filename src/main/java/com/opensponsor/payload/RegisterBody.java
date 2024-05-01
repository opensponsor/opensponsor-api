package com.opensponsor.payload;

import com.opensponsor.entitys.CountryCodes;
import com.opensponsor.enums.E_SEX;
import io.quarkus.runtime.annotations.IgnoreProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Comment;

public class RegisterBody {
    @Comment("user name")
    @Size(min = 2, max = 32)
    @NotBlank
    @NotEmpty
    @NotNull
    public String username;


    @Comment("legal name")
    @Size(min = 2, max = 32)
    @NotBlank
    @NotEmpty
    @NotNull
    public String legalName;


    @Comment("country code")
    @NotNull
    public CountryCodes countryCode;

    @Comment("User Sex")
    @Enumerated
    public E_SEX sex;

    @Comment("password")
    @Size(min = 8, max = 32)
    @NotBlank
    @NotEmpty
    @NotNull
    public String password;
}
