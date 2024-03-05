import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest extends DeleteCourier {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Login as courier successful")
    public void login() {
        CourierLogin courierLogin = new CourierLogin("Tes2t998321", "123456");
        Response response =
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLogin)
                .when()
                .post("/api/v1/courier/login");
                response.then().assertThat().body("id", notNullValue());

    }

    @Test
    @DisplayName("Wrong login test")
    public void invalidLoginFieldTest() {
        CourierLogin courierLogin = new CourierLogin(null, "123456");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierLogin)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Wrong password test")
    public void invalidPasswordFieldTest() {
        CourierLogin courierLogin = new CourierLogin("Tes2t998321", null);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierLogin)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("User doesn't exist")
    public void userDoesNotExistTest() {
        CourierLogin courierLogin = new CourierLogin("Tes2t99831", "1234");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierLogin)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        deleteCourier();
    }

}
