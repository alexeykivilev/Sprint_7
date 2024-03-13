import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ListOfOrdersTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Get list of orders test")
    public void listOfOrdersTest() {
                given()
                        .get("orders?courierId=269318&nearestStation=[\"1\", \"2\"]")
                        .then().assertThat().body("orders", notNullValue());
    }
}
