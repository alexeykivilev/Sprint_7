import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreateTest extends DeleteCourier {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }
    NewCourier newCourier = new NewCourier("Tes2t998321", "123456", "ivano");

    @Test
    @DisplayName("Send POST request to /api/v1/courier")
    public void createNewCourierTest() {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(newCourier)
                        .when()
                        .post("/api/v1/courier");
        response.then().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Check status code for /api/v1/courier")
    public void postStatusCodeTest() {
        Response response =
        given()
                .header("Content-type", "application/json")
                .and()
                .body(newCourier)
                .when()
                .post("/api/v1/courier");
        response.then().statusCode(201);
    }

    @Test
    @DisplayName("Cannot create duplicate courier")
    public void duplicateCourierTest() {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(newCourier)
                        .when()
                        .post("/api/v1/courier");
        response.then().statusCode(409);
    }

    @Test
    @DisplayName("Missing login field")
    public void missingLoginFieldTest() {
        NewCourier newCourier = new NewCourier(null, "123456", "ivano");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(newCourier)
                        .when()
                        .post("/api/v1/courier");
        response.then().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Missing password field")
    public void missingPasswordFieldTest() {
        NewCourier newCourier = new NewCourier("test12343asdads", null, "ivano");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(newCourier)
                        .when()
                        .post("/api/v1/courier");
        response.then().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void tearDown() {
        deleteCourier();
    }
}
