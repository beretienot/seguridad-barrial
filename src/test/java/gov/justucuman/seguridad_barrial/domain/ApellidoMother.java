package gov.justucuman.seguridad_barrial.domain;

public class ApellidoMother {

    public static Apellido valid() {
           return new Apellido("Apellido" + java.util.UUID.randomUUID().toString().substring(0, 8));
    }
}
