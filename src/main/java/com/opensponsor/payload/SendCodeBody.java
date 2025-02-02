package com.opensponsor.payload;


import com.opensponsor.entitys.CountryCodes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SendCodeBody {
    @NotNull()
    public CountryCodes countryCode;

    @Size(min = 11, max = 11)
    @NotBlank()
    @NotNull()
    @NotEmpty()
    public String phoneNumber;
}
