package gov.justucuman.seguridad_barrial.domain;

import java.util.UUID;

public class PropietarioMother {

    public static Propietario valid() {
        return new Propietario(
            UUID.randomUUID(),
            NombreMother.valid(),
            ApellidoMother.valid(),
            DniMother.valid(),
            DireccionMother.valid(),
            TelefonoMother.valid(),
            EmailMother.valid()
        );
    }

    public static Propietario withoutOptionalFields() {
        return new Propietario(
            UUID.randomUUID(),
            NombreMother.valid(),
            ApellidoMother.valid(),
            DniMother.valid(),
            DireccionMother.valid(),
            null,
            null
        );
    }
}
