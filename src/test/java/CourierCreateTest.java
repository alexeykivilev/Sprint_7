import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreateTest {
    private String login = RandomStringUtils.randomAlphabetic(10);
    ;
    private String password = RandomStringUtils.randomNumeric(10);
    ;
    private String firstName = RandomStringUtils.randomAlphabetic(10);
    ;
    private String courierId;


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    NewCourier newCourier = new NewCourier(login, password, firstName);

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
        response.then().statusCode(201).assertThat().body("ok", equalTo(true));
        courierId = response.jsonPath().getString("id");
    }

    @Test
    @DisplayName("Cannot create duplicate courier")
    public void duplicateCourierTest() {
        File json = new File("src/test/resources/duplicateCourier.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/v1/courier");
        response.then().statusCode(419);
    }

    @Test
    @DisplayName("Missing login field")
    public void missingLoginFieldTest() {
        NewCourier newCourier = new NewCourier(null, password, firstName);
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
        NewCourier newCourier = new NewCourier(login, null, firstName);
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
        if (courierId != null) {
            Response response =
                    given()
                            .when()
                            .delete("/api/v1/courier/" + courierId);
            response.then().statusCode(200).assertThat().body("ok", equalTo(true));
        }
    }
}
