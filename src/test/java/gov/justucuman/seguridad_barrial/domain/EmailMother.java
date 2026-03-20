package gov.justucuman.seguridad_barrial.domain;

public class EmailMother {

    public static Email valid() {
           String uuid = java.util.UUID.randomUUID().toString().substring(0, 8);
           return new Email("usuario" + uuid + "@example.com");
    }

    public static Email withNull() {
        return new Email(null);
    }

    public static String invalidFormat() {
        return "no-es-un-email";
    }
}
