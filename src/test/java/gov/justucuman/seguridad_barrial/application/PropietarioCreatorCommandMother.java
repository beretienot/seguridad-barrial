package gov.justucuman.seguridad_barrial.application;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class PropietarioCreatorCommandMother {

    public static PropietarioCreatorCommand valid() {
        int dni = ThreadLocalRandom.current().nextInt(1000000, 99999999);
        return PropietarioCreatorCommand.builder()
                .id(UUID.randomUUID())
                .nombre("Nombre-" + UUID.randomUUID().toString().substring(0, 8))
                .apellido("Apellido-" + UUID.randomUUID().toString().substring(0, 8))
                .dni(String.valueOf(dni))
                .direccion("Calle " + UUID.randomUUID().toString().substring(0, 8))
                .telefono(String.valueOf(ThreadLocalRandom.current().nextLong(1000000000L, 9999999999L)))
                .email(UUID.randomUUID().toString().substring(0, 8) + "@example.com")
                .build();
    }

    public static PropietarioCreatorCommand withInvalidDni() {
        return PropietarioCreatorCommand.builder()
                .id(UUID.randomUUID())
                .nombre("Nombre-" + UUID.randomUUID().toString().substring(0, 8))
                .apellido("Apellido-" + UUID.randomUUID().toString().substring(0, 8))
                .dni("ABC")
                .direccion("Calle " + UUID.randomUUID().toString().substring(0, 8))
                .telefono(String.valueOf(ThreadLocalRandom.current().nextLong(1000000000L, 9999999999L)))
                .email(UUID.randomUUID().toString().substring(0, 8) + "@example.com")
                .build();
    }

    public static PropietarioCreatorCommand withoutNombre() {
        int dni = ThreadLocalRandom.current().nextInt(1000000, 99999999);
        return PropietarioCreatorCommand.builder()
                .id(UUID.randomUUID())
                .nombre(null)
                .apellido("Apellido-" + UUID.randomUUID().toString().substring(0, 8))
                .dni(String.valueOf(dni))
                .direccion("Calle " + UUID.randomUUID().toString().substring(0, 8))
                .build();
    }
}
