package org.example.order;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.courier.CourierClient;
import org.example.models.Courier;
import org.example.models.OrderScooter;
import static io.restassured.RestAssured.given;

public class OrderScooterClient {

    private static final String CREATING_ORDER_URL = "/api/v1/orders";
    private static final String ACCEPT_ORDER_URL = "/api/v1/orders/accept/";
    private static final String CANCEL_ORDER_URL = "/api/v1/orders/cancel";
    private static final String GET_ALL_ORDERS = "/api/v1/orders";
    private static final String FINISH_ORDER = "/api/v1/orders/finish/:id";
    private static final String GET_ORDER_BY_NUMBER = "/api/v1/orders/track";
    CourierClient courierClient = new CourierClient();

    @Step("Send POST request to /api/v1/orders - создание заказа")
    public Response create(OrderScooter orderScooter){
        return given()
                .header("Content-type", "application/json")
                .body(orderScooter)
                .and()
                .post(CREATING_ORDER_URL);
    }

    @Step("Send POST request to /api/v1/orders - получение track заказа")
    public String getTrack(OrderScooter orderScooter){
        Response response = given()
                .header("Content-type", "application/json")
                .body(orderScooter)
                .and()
                .post(CREATING_ORDER_URL);
        return response.jsonPath().getString("track");
    }

    @Step("Send PUT request to /api/v1/orders/cancel - отмена заказа")
    public Response cancel(OrderScooter orderScooter){
        String track = getTrack(orderScooter);
        return given()
                .header("Content-type", "application/json")
                .body("{\"track\": " + track + " }")
                .put(CANCEL_ORDER_URL);
    }

    @Step("Send PUT request to /api/v1/orders/accept/:id - принять заказ")
    public Response assept(OrderScooter orderScooter, Courier courier){
        String  courierId = courierClient.getIdCourier(courier);
        String id = getId(orderScooter);
        return given()
                .header("Content-type", "application/json")
                .queryParam("courierId", courierId)
                .pathParam("id", id)
                .put(ACCEPT_ORDER_URL + "{id}");
    }

    @Step("Send PUT request to /api/v1/orders/finish/:id - завершить заказ")
    public Response finish(OrderScooter orderScooter){
        String idOrders = getId(orderScooter);
        return given()
                .header("Content-type", "application/json")
                .pathParam("idOrders", idOrders)
                .get(FINISH_ORDER + "{idOrders}");
    }

    @Step("Send GET request to /api/v1/orders/track - получить id заказа")
    public String getId(OrderScooter orderScooter) {
        String t = getTrack(orderScooter);
        Response response =
                given()
                        .queryParam("t", t)
                        .get(GET_ORDER_BY_NUMBER);
        return response.jsonPath().getString("order.id");
    }

    @Step("Send GET request to /api/v1/orders - получить список всех заказов")
    public Response getAll(){
        return given()
                .get(GET_ALL_ORDERS);
    }

    @Step("Send GET request to /api/v1/orders - получить список всех заказов курьера")
    public Response getAllCourierOrders(Courier courier){
        String courierId = courierClient.getIdCourier(courier);
        return given()
                .queryParam("courierId", courierId)
                .get(GET_ALL_ORDERS);
    }

    @Step("Send GET request to /api/v1/orders - получить список всех заказов курьера на станциях Бульвар Рокоссовского(1) или Черкизовская(2)")
    public Response getAllCourierOrdersAtStations(Courier courier){
        String courierId = courierClient.getIdCourier(courier);
        return given()
                .queryParam("courierId", courierId)
                .queryParam("nearestStation", "[\"1", "2\"]")
                .get(GET_ALL_ORDERS);
    }
}
