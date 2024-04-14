package com.opensponsor.utils;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SecurityTools {
    public String generatePassword(String plaintext) {
        return BcryptUtil.bcryptHash(plaintext);
    }
}
