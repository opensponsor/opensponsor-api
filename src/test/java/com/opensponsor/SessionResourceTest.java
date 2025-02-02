package com.opensponsor;

import com.opensponsor.entitys.CountryCode;
import com.opensponsor.enums.E_SEX;
import com.opensponsor.payload.RegisterBody;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class SessionResourceTest {
    @Test
    void testRegisterUser() {
        RegisterBody body = new RegisterBody();
        body.username = "霍建营";
        body.slug = "huo-jian-ying";
        body.countryCode = CountryCode.findById(UUID.fromString("0466037a-b655-4629-a748-35ca3789d68f"));
        body.sex = E_SEX.MAN;
        body.password = "12345678";

        given()
            .when().contentType(ContentType.JSON)
            .when().body(body)
            .when().post("/session/register")
            .then()
            .statusCode(200)
            .body(anything());
    }
}
