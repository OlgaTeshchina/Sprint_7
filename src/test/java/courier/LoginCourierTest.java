package courier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.courier.CourierClient;
import org.example.models.Courier;
import org.example.models.CourierCred;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.Utils;
import static org.example.courier.CourierGenerator.randomCourier;
import static org.example.models.YandexScooterUrl.YANDEX_SCOOTER_URL;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginCourierTest {

    CourierClient courierClient = new CourierClient();
    Courier courier = randomCourier();

    @Before
    public void setUp(){

        RestAssured.baseURI = YANDEX_SCOOTER_URL;
        courierClient.create(courier);
    }

    @Test
    @DisplayName("Вход курьера в систему с логином и паролем возможен")
    public void loginCourierWithLoginPasswordPossible(){
        Response response = courierClient.login(CourierCred.fromCourier(courier));
        assertTrue(response.jsonPath().getString("id") != null);
        assertEquals("Неверный статус код ответа", 200, response.statusCode());
    }

    @Test
    @DisplayName("Вход курьера в систему только с логином невозможен")
    public void loginCourierWithOnlyLoginImpossible(){
        CourierCred courierWithOnlyLogin = CourierCred.fromCourier(courier);
        courierWithOnlyLogin.setPassword("");

        Response response = courierClient.login(courierWithOnlyLogin);
        response
                .then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
        assertEquals("Неверный статус код ответа", 400, response.statusCode());
    }

    @Test
    @DisplayName("Вход курьера в систему только с паролем невозможен")
    public void loginCourierWithOnlyPasswordImpossible() {
        CourierCred courierWithOnlyPassword = CourierCred.fromCourier(courier);
        courierWithOnlyPassword.setLogin("");

        Response response = courierClient.login(courierWithOnlyPassword);
        response
                .then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
        assertEquals("Неверный статус код ответа", 400, response.statusCode());
    }

    @Test
    @DisplayName("Вход курьера в систему без логина и пароля невозможен")
    public void loginCourierWithoutLoginPasswordImpossible() {
        CourierCred courierWithoutLoginPassword = CourierCred.fromCourier(courier);
        courierWithoutLoginPassword.setLogin("");
        courierWithoutLoginPassword.setPassword("");

        Response response = courierClient.login(courierWithoutLoginPassword);
        response
                .then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
        assertEquals("Неверный статус код ответа", 400, response.statusCode());
    }

    @Test
    @DisplayName("Вход курьера в систему c неверным логином невозможен")
    public void loginCourierWithIncorrectLoginImpossible() {
        CourierCred courierWithIncorrectLogin = CourierCred.fromCourier(courier);
        courierWithIncorrectLogin.setPassword(Utils.randomString(8));

        Response response = courierClient.login(courierWithIncorrectLogin);
        response
                .then()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
        assertEquals("Неверный статус код ответа", 404, response.statusCode());
    }

    @Test
    @DisplayName("Вход курьера в систему c неверным паролем невозможен")
    public void loginCourierWithIncorrectPasswordImpossible() {
        CourierCred courierWithIncorrectPassword = CourierCred.fromCourier(courier);
        courierWithIncorrectPassword.setPassword(Utils.randomString(8));

        Response response = courierClient.login(courierWithIncorrectPassword);
        response
                .then()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
        assertEquals("Неверный статус код ответа", 404, response.statusCode());
    }

        @After
        public void tearDown () {
            courierClient.remove(courier);
        }
}
