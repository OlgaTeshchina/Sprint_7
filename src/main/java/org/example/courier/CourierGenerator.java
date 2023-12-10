package org.example.courier;
import org.example.models.Courier;
import utils.Utils;

public class CourierGenerator {

    public static Courier randomCourier() {
        return new Courier()
                .withLogin(Utils.randomString(8))
                .withPassword(Utils.randomString(16))
                .withFirstName(Utils.randomString(10));
    }
}
