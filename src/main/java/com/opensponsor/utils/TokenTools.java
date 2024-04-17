package com.opensponsor.utils;

import com.opensponsor.entitys.User;
import com.opensponsor.entitys.UserToken;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.jwt.Claims;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@ApplicationScoped
public class TokenTools {
    public void generate(User user, List<String> roles) {
        String date = LocalDate.now()
            .plusYears(1)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String tokenString = Jwt.issuer("https://opensponsor.com")
            .upn(user.name)
            .groups(new HashSet<>(roles))
            .expiresIn(Duration.ofDays(30))
            // .expiresAt(now.plus(30, ChronoUnit.DAYS))
            .claim(Claims.birthdate.name(), date)
            .sign();

        if(user.token == null) {
            UserToken userToken = new UserToken();
            userToken.token = tokenString;
            userToken.user = user;
            user.token = userToken;
            user.persistAndFlush();
        } else {
            user.token.token = tokenString;
            user.token.persistAndFlush();
        }
    }
}
