package com.opensponsor.payload;


import com.opensponsor.enums.E_SEX;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.Comment;

public class UpdateUser {
    @Comment("user name")
    @Size(min = 2, max = 32)
    @Schema(description = "username", required = true)
    public String username;

    @Comment("url slug")
    @Size(min = 2, max = 32)
    @Schema(description = "url slug", required = true, minLength = 2, maxLength = 32)
    public String slug;

    @Comment("website")
    @Size(min = 2, max = 128)
    @Schema(description = "website url", minLength = 2, maxLength = 128)
    public String website;

    @Comment("User sex")
    @Enumerated(EnumType.STRING)
    @Schema(description = "sex")
    public E_SEX sex;
}
