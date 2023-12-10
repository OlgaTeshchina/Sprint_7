package order;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.courier.CourierClient;
import org.example.models.Courier;
import org.example.models.OrderScooter;
import org.example.order.OrderScooterClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.example.courier.CourierGenerator.randomCourier;
import static org.example.models.YandexScooterUrl.YANDEX_SCOOTER_URL;
import static org.example.order.OrderScooterGenerator.randomOrderScooter;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

public class OrderListTest {

    Courier courier = randomCourier();
    OrderScooter orderScooter = randomOrderScooter();
    OrderScooter orderScooterTwo = randomOrderScooter();
    CourierClient courierClient = new CourierClient();
    OrderScooterClient orderScooterClient = new OrderScooterClient();

    @Before
    public void setUp(){
        RestAssured.baseURI = YANDEX_SCOOTER_URL;
        courierClient.create(courier);

        //Курьер принял заказ
        orderScooterClient.create(orderScooter);
        orderScooterClient.assept(orderScooter, courier);
        //Курьер принял и завершил заказ2
        orderScooterClient.create(orderScooterTwo);
        orderScooterClient.assept(orderScooterTwo, courier);
        orderScooterClient.finish(orderScooterTwo);
    }

    @Test
    @DisplayName("Получение списка всех заказов")
    public void checkListAll(){
        Response response = orderScooterClient.getAll();
        response
                .then()
                .assertThat()
                .body("orders", notNullValue());
        assertEquals("Неверный статус код ответа1", 200, response.statusCode());
    }

    @Test
    @DisplayName("Получение списка всех активных и завершенных заказов курьера")
    public void checkListOfCourierOrders(){
        Response response = orderScooterClient.getAllCourierOrders(courier);
        response
                .then()
                .assertThat()
                .body("orders[1].courierId", equalTo(Integer.parseInt(courierClient.getIdCourier(courier))))
                .body("orders[2].courierId", equalTo(Integer.parseInt(courierClient.getIdCourier(courier))));
        assertEquals("Неверный статус код ответа2", 200, response.statusCode());
    }

    @Test
    @DisplayName("Получение списка всех заказов курьера на станциях Бульвар Рокоссовского(1) или Черкизовская(2)")
    public void checkListOfCourierOrdersAtStation(){
        OrderScooter orderScooterStation1 = new OrderScooter()
                .withMetroStation("1");
        OrderScooter orderScooterStation2 = new OrderScooter()
                .withMetroStation("2");
        //Курьер принимает заказы со станций Бульвар Рокоссовского(1) и Черкизовская(2)
        orderScooterClient.assept(orderScooterStation1, courier);
        orderScooterClient.assept(orderScooterStation2, courier);

        Response response = orderScooterClient.getAllCourierOrdersAtStations(courier);
        response
                .then()
                .assertThat()
                .body("orders[1].metroStation", equalTo(1))
                .body("orders[2].metroStation", equalTo(2));
        assertEquals("Неверный статус код ответа3", 200, response.statusCode());

        //Отмена заказов
        Response response1 = orderScooterClient.cancel(orderScooterStation1);
        assertEquals("Неверный статус код ответа4", 200, response1.statusCode());
        Response response2 = orderScooterClient.cancel(orderScooterStation1);
        assertEquals("Неверный статус код ответа5", 200, response2.statusCode());
    }

    @After
    public void tearDown(){
        courierClient.remove(courier);
        orderScooterClient.cancel(orderScooter);
    }

}
