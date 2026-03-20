package gov.justucuman.seguridad_barrial.domain;

public class NombreMother {

    public static Nombre valid() {
           return new Nombre("Nombre" + java.util.UUID.randomUUID().toString().substring(0, 8));
    }
}
