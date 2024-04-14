package com.opensponsor.payload;

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
    @Column(unique = true, length = 32, nullable = false)
    @Size(min = 2, max = 32)
    @NotBlank
    @NotEmpty
    @NotNull
    public String name;

    @Comment("legal name")
    @Column(length = 32, nullable = false)
    @Size(min = 2, max = 32)
    @NotBlank
    @NotEmpty
    @NotNull
    public String legalName;

    @Comment("User avatar")
    @Enumerated
    public E_SEX sex;

    @Comment("password")
    @Column(length = 32, nullable = false)
    @Size(min = 6, max = 32)
    @NotBlank
    @NotEmpty
    @NotNull
    public String password;
}
