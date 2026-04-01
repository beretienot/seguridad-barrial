package gov.justucuman.seguridad_barrial.domain;

import java.util.UUID;

public class DireccionMother {

    public static Direccion valid() {
        return new Direccion("Calle " + UUID.randomUUID().toString().substring(0, 8));
    }

    public static String invalidBlank() {
        return "   ";
    }

    public static String invalidNull() {
        return null;
    }
}
