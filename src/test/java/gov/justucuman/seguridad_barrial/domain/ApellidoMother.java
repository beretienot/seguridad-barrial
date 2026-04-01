package gov.justucuman.seguridad_barrial.domain;

import java.util.UUID;

public class ApellidoMother {

    public static Apellido valid() {
        return new Apellido("Apellido-" + UUID.randomUUID().toString().substring(0, 8));
    }

    public static String invalidBlank() {
        return "   ";
    }

    public static String invalidNull() {
        return null;
    }
}
