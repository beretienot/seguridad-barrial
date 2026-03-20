package gov.justucuman.seguridad_barrial.domain;

public class DireccionMother {

    public static Direccion valid() {
           return new Direccion("Calle " + java.util.UUID.randomUUID().toString().substring(0, 4) + " 1" + (int)(Math.random()*1000));
    }
}
