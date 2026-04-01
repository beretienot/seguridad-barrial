package gov.justucuman.seguridad_barrial.domain;

import java.util.concurrent.ThreadLocalRandom;

public class TelefonoMother {

    public static Telefono valid() {
        long number = ThreadLocalRandom.current().nextLong(1000000000L, 9999999999L);
        return new Telefono(String.valueOf(number));
    }

    public static Telefono withNull() {
        return new Telefono(null);
    }

    public static String invalidBlank() {
        return "   ";
    }
}
