package gok.api.application.presentation.rest;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class HealthCheckControllerTest {

    @Test
    public void testHealthCheck() {
        given()
            .when().get("/health")
            .then()
                .statusCode(200)
                .body("status", is("Running!"));
    }
} 