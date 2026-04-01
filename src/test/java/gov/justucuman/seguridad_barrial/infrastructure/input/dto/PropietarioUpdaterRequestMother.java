package gov.justucuman.seguridad_barrial.infrastructure.input.dto;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class PropietarioUpdaterRequestMother {

    public static PropietarioUpdaterRequest valid() {
        int dni = ThreadLocalRandom.current().nextInt(1000000, 99999999);
        PropietarioUpdaterRequest request = new PropietarioUpdaterRequest();
        request.setNombre("Nombre-" + UUID.randomUUID().toString().substring(0, 8));
        request.setApellido("Apellido-" + UUID.randomUUID().toString().substring(0, 8));
        request.setDni(String.valueOf(dni));
        request.setDireccion("Calle " + UUID.randomUUID().toString().substring(0, 8));
        request.setTelefono(String.valueOf(ThreadLocalRandom.current().nextLong(1000000000L, 9999999999L)));
        request.setEmail(UUID.randomUUID().toString().substring(0, 8) + "@example.com");
        return request;
    }

    public static PropietarioUpdaterRequest withoutNombre() {
        PropietarioUpdaterRequest request = valid();
        request.setNombre(null);
        return request;
    }
}
