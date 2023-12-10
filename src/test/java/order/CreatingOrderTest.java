package order;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.models.OrderScooter;
import org.example.order.OrderScooterClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.example.models.YandexScooterUrl.YANDEX_SCOOTER_URL;
import static org.example.order.OrderScooterGenerator.randomOrderScooter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class CreatingOrderTest {

    private final String[] color;
    OrderScooterClient orderScooterClient = new OrderScooterClient();
    OrderScooter order = randomOrderScooter();

    public CreatingOrderTest(String[] color){
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getInformationAboutColor(){
        return new Object[][]{
                {new String[]{"BLACK", "GREY"}},
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{}}
        };
    }

    @Before
    public void setUp(){
        RestAssured.baseURI = YANDEX_SCOOTER_URL;

    }

    @Test
    @DisplayName("В заказе можно указать различные комбинации цветов BLACK и GREY")
    public void checkSetColorBlackGray(){
        order.setColor(color);
        Response respons = orderScooterClient.create(order);
        assertTrue(respons.jsonPath().getString("track") != null);
        assertEquals("Неверный статус код ответа", 201, respons.statusCode());
    }

   @Before
   public void tearDown(){
      orderScooterClient.cancel(order);
   }
}
