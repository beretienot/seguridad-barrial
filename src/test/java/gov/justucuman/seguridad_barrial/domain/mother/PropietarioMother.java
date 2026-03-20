
package gov.justucuman.seguridad_barrial.domain.mother;

import gov.justucuman.seguridad_barrial.domain.NombreMother;
import gov.justucuman.seguridad_barrial.domain.ApellidoMother;
import gov.justucuman.seguridad_barrial.domain.DniMother;
import gov.justucuman.seguridad_barrial.domain.DireccionMother;
import gov.justucuman.seguridad_barrial.domain.TelefonoMother;
import gov.justucuman.seguridad_barrial.domain.EmailMother;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import java.util.UUID;

public class PropietarioMother {
    public static Propietario valid() {
        return new Propietario(
            UUID.fromString("11111111-1111-1111-1111-111111111111"),
            NombreMother.valid(),
            ApellidoMother.valid(),
            DniMother.valid(),
            DireccionMother.valid(),
            TelefonoMother.valid(),
            EmailMother.valid()
        );
    }
    public static Propietario withId(UUID id) {
        return new Propietario(
            id,
            NombreMother.valid(),
            ApellidoMother.valid(),
            DniMother.valid(),
            DireccionMother.valid(),
            TelefonoMother.valid(),
            EmailMother.valid()
        );
    }
}
