package gov.justucuman.seguridad_barrial.domain;

import java.util.UUID;

public class NombreMother {

    public static Nombre valid() {
        return new Nombre("Nombre-" + UUID.randomUUID().toString().substring(0, 8));
    }

    public static String invalidBlank() {
        return "   ";
    }

    public static String invalidNull() {
        return null;
    }
}
