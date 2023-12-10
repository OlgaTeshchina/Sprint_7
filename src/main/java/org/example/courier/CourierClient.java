package org.example.courier;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.models.Courier;
import org.example.models.CourierCred;
import static io.restassured.RestAssured.given;

public class CourierClient {

    private static final String COURIER_URL = "/api/v1/courier";
    private static final String COURIER_LOGIN_IN_SYSTEM_URL = "/api/v1/courier/login";
    private static final String COURIER_DELETE_URL = "/api/v1/courier/";

    @Step("Send POST request to /api/v1/courier - создание курьера")
    public Response create(Courier courier){
        return  given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .post(COURIER_URL);
    }

    @Step("Send POST request to /api/v1/courier/login - вход курьера на сайт")
    public Response login(CourierCred courierCred){
               return   given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierCred)
                        .post(COURIER_LOGIN_IN_SYSTEM_URL);
    }

    @Step("Send POST request to /api/v1/courier/login - получение id курьера")
    public String getIdCourier(Courier courier){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .post(COURIER_LOGIN_IN_SYSTEM_URL);
        return response.jsonPath().getString("id");
    }

    @Step("Send DELETE request to /api/v1/courier/:id - удаление курьера")
    public Response remove(Courier courier){
        String id = getIdCourier(courier);
        return given()
                .header("Content-type", "application/json")
                .and()
                .pathParam("id", id)
                .body("{\"id\":\"" + id + "\"}")
                .when()
                .delete(COURIER_DELETE_URL + "{id}");
    }


}
