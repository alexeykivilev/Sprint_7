import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DeleteCourier extends CourierLogin {
    public void deleteCourier() {
        CourierLogin courierLogin = new CourierLogin("Tes2t99831", "123456");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierLogin)
                        .when()
                        .post("/api/v1/courier/login");
        String id = response.jsonPath().getString("id");
        given()
                .when()
                .delete("/api/v1/courier/login " + id);
    }
}
