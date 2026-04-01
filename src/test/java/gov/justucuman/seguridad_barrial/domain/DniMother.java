package gov.justucuman.seguridad_barrial.domain;

import java.util.concurrent.ThreadLocalRandom;

public class DniMother {

    public static Dni valid() {
        int digits = ThreadLocalRandom.current().nextInt(1000000, 99999999);
        return new Dni(String.valueOf(digits));
    }

    public static Dni withSevenDigits() {
        int digits = ThreadLocalRandom.current().nextInt(1000000, 9999999);
        return new Dni(String.valueOf(digits));
    }

    public static String invalidWithLetters() {
        return "1234abcd";
    }

    public static String invalidWithLessThanSevenDigits() {
        return "123456";
    }

    public static String invalidWithMoreThanEightDigits() {
        return "123456789";
    }
}
