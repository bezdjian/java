package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class TodoResourceTest {

    @Test
    void testHelloEndpoint() {
        given()
                .when().get("/")
                .then()
                .statusCode(200);
    }

}