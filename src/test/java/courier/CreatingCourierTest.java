package courier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.courier.CourierClient;
import org.example.models.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.example.courier.CourierGenerator.randomCourier;
import static org.example.models.YandexScooterUrl.YANDEX_SCOOTER_URL;
import static org.junit.Assert.assertEquals;

public class CreatingCourierTest {

    CourierClient courierClient = new CourierClient();
    Courier courier = randomCourier();

    @Before
    public void setUp(){
        RestAssured.baseURI = YANDEX_SCOOTER_URL;
    }

    @Test
    @DisplayName("Создание курьера с логином паролем и именем возможно")
    public void loginCourierWithLoginPasswordFirsNamePossible(){
        Response response = courierClient.create(courier);
        assertEquals("Неверное тело ответа", "{\"ok\":true}", response.body().asString());
        assertEquals("Неверный статус код ответа", 201, response.statusCode());
    }

    @After
    public void tearDown(){
        courierClient.getIdCourier(courier);
        courierClient.remove(courier);
    }
}
