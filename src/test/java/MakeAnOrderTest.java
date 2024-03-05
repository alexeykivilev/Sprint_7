import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class MakeAnOrderTest {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<String> color;

    public MakeAnOrderTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] orderData() {
        return new Object[][] {
                {"Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", List.of("BLACK")},
                {"Ivan", "Ivanov", "Moskwa, 1", "1", "+7 900 444 30 30", 1, "2020-06-06", "make it as quick as possible", null},
                {"Petr", "Petrov", "Moskwa, 643", "7", "+7 912 345 67 89", 3, "2020-06-06", "test", List.of("GREY")},
                {"Lena", "Lenina", "Konoha, 120", "2", "+7 800 355 35 36", 2, "2020-06-06", "lol", List.of("BLACK", "GREY")}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Send POST to /api/v1/orders")
    public void makeAnOrder() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(order)
                        .when()
                        .post("api/v1/orders");
        response.then().assertThat().body("track", notNullValue());
    }
}
