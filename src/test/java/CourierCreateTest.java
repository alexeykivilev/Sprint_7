import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreateTest {
    private String login = RandomStringUtils.randomAlphabetic(10);;
    private String password = RandomStringUtils.randomNumeric(10);;
    private String firstName = RandomStringUtils.randomAlphabetic(10);;


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }
    NewCourier newCourier = new NewCourier(login, password, firstName);

    @Test
    @DisplayName("Creating new courier test")
    public void createNewCourier() {
        Response response = createNewCourierTest();
        Response response2 = duplicateCourierTest();

    }

    @Step("Send POST request to /api/v1/courier")
    public Response createNewCourierTest() {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(newCourier)
                        .when()
                        .post("/api/v1/courier");
        response.then().statusCode(201).assertThat().body("ok", equalTo(true));
        return response;
    }

    @Step("Cannot create duplicate courier")
    public Response duplicateCourierTest() {
        Response response2 =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(newCourier)
                        .when()
                        .post("/api/v1/courier");
        response2.then().statusCode(409);
        return response2;
    }

    @Test
    @DisplayName("Missing fields test")
    public void missingFieldsTest() {
        Response response = missingLoginFieldTest();
        Response response1 = missingPasswordFieldTest();
    }

    @Step("Missing login field")
    public Response missingLoginFieldTest() {
        NewCourier newCourier = new NewCourier(null, "123456", "ivano");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(newCourier)
                        .when()
                        .post("/api/v1/courier");
        response.then().body("message", equalTo("Недостаточно данных для создания учетной записи"));
        return response;
    }

    @Step("Missing password field")
    public Response missingPasswordFieldTest() {
        NewCourier newCourier = new NewCourier("test12343asdads", null, "ivano");
        Response response1 =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(newCourier)
                        .when()
                        .post("/api/v1/courier");
        response1.then().body("message", equalTo("Недостаточно данных для создания учетной записи"));
        return response1;
    }
}
