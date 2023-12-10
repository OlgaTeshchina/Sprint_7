package org.example.order;
import org.example.models.OrderScooter;
import utils.Utils;
import java.util.Random;
import static utils.Utils.randomString;

public class OrderScooterGenerator {
    public static OrderScooter randomOrderScooter(){
        Random random = new Random();
        return new OrderScooter()
                .withFirstName(Utils.randomString(5))
                .withLastName(Utils.randomString(5))
                .withAddress(Utils.randomString(5))
                .withMetroStation(Utils.randomString(5))
                .withPhone(Utils.randomString(5))
                .withRentTime(Utils.getRandomNumber(1,3))
                .withDeliveryDate(Utils.getRandomDeliveryDate())
                .withComment(Utils.randomString(5))
                .withColor(new String[]{randomString(3), randomString(3)});
    }
}
