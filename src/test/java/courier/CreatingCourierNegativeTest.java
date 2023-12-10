package courier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.courier.CourierClient;
import org.example.models.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.Utils;
import static org.example.courier.CourierGenerator.randomCourier;
import static org.example.models.YandexScooterUrl.YANDEX_SCOOTER_URL;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.equalTo;

public class CreatingCourierNegativeTest {

    CourierClient courierClient = new CourierClient();
    Courier courier = randomCourier();
    
    @Before
    public void setUp(){
        RestAssured.baseURI = YANDEX_SCOOTER_URL;
    }

    @Test
    @DisplayName("Создание курьера только с логином, без пароля невозможно")
    public void createCourierOnlyWithLoginFalse(){
        Courier courierOnlyWithLogin = new Courier()
                .withLogin(Utils.randomString(8));
        Response response = courierClient.create(courierOnlyWithLogin);
        response
                .then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
        assertEquals("Неверный статус код ответа", 400, response.statusCode());
    }

    @Test
    @DisplayName("Создание курьера только с паролем, без логина невозможно")
    public void createCourierOnlyWithPasswordImpossible(){
        Courier courierOnlyWithPassword = new Courier()
                .withPassword(Utils.randomString(10));
        Response response = courierClient.create(courierOnlyWithPassword);
        response
                .then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
        assertEquals("Неверный статус код ответа", 400, response.statusCode());
    }

    @Test
    @DisplayName("Создание курьера с логином, который уже есть невозможно")
    public void createCourierWithSameLoginImpossible(){
        Courier сourierWithSameLogin = new Courier()
                .withLogin(courier.getLogin())
                .withPassword(Utils.randomString(10))
                .withFirstName(Utils.randomString(16));

        Response responseFirst = courierClient.create(courier);
        assertEquals("Неверный статус код ответа", 201, responseFirst.statusCode());

        Response responseSecond = courierClient.create(сourierWithSameLogin);
        responseSecond
                .then()
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        assertEquals("Неверный статус код ответа", 409, responseSecond.statusCode());
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров невозможно")
    public void createTwoIdenticalCouriersImpossible(){
        Courier sameCourier = new Courier()
                .withLogin(courier.getLogin())
                .withPassword(courier.getPassword())
                .withFirstName(courier.getFirstName());

        Response responseFirst = courierClient.create(courier);
        assertEquals("Неверный статус код ответа", 201, responseFirst.statusCode());

        Response responseSecond = courierClient.create(sameCourier);
        responseSecond
                .then()
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        assertEquals("Неверный статус код ответа", 409, responseSecond.statusCode());
    }

    @After
    public void tearDown(){
       if(courierClient.getIdCourier(courier) != null) {
           courierClient.remove(courier);
       }
    }
}
