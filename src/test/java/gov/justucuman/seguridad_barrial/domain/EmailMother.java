package gov.justucuman.seguridad_barrial.domain;

import java.util.UUID;

public class EmailMother {

    public static Email valid() {
        return new Email(UUID.randomUUID().toString().substring(0, 8) + "@example.com");
    }

    public static Email withNull() {
        return new Email(null);
    }

    public static String invalidFormat() {
        return "no-es-un-email";
    }
}
