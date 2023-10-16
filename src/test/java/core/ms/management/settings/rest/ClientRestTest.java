package core.ms.management.settings.rest;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ClientRestTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/rest/client")
          .then()
             .statusCode(200)
             .body(is("Hello RESTEasy"));
    }

}