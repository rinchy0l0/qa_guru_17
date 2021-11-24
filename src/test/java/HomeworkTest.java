import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeworkTest {
    @Test
    void createUserPositive() {
        given()
                .contentType(JSON)
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body("name", Matchers.is("morpheus"));
    }

    @Test
    void createUserWithoutName() {

        given()
                .contentType(JSON)
                .body("{\n" +

                        "    \"job\": \"leader\"\n" +
                        "}")
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)

                .body("job", Matchers.is("leader"));
    }

    @Test
    void updateUser() {
        given()
                .contentType(JSON)
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .when()
                .post("https://reqres.in/api/users/2")
                .then()
                .statusCode(201)
                .body("job", Matchers.is("zion resident"));
    }

    @Test
    void updateUserNegative() {
        given()
                .contentType(JSON)
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "}")
                .when()
                .post("https://reqres.in/api/users/2")
                .then()
                .statusCode(400);
    }

    @Test
    void singleUserNotFound() {
        given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200);
        ;
    }

    @Test
    void getList() {
        get("https://reqres.in/api/unknown")
                .then()
                .statusCode(200)
                .body("total", Matchers.is(12));
    }

    @Test
    void registerUserUnsuccessful() {

        given()
                .contentType(JSON)
                .body("{\n" +
                        "    \"\": \"sydney@fife\"\n" +
                        "}")
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(400)

                .body("error", Matchers.is("Missing email or username"));
    }
}


