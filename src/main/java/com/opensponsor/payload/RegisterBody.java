package com.opensponsor.payload;

import com.opensponsor.entitys.CountryCode;
import com.opensponsor.enums.E_SEX;
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

    @Comment("url slug")
    @NotBlank
    @NotEmpty
    @NotNull
    public String slug;

    @Comment("phone number")
    @NotBlank
    @NotEmpty
    @NotNull
    public String phoneNumber;

    @Comment("country code")
    @NotNull
    public CountryCode countryCode;

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
