package gov.justucuman.seguridad_barrial.infrastructure.input.dto;

import java.util.UUID;

public class PropietarioCreatorRequestMother {

    public static final UUID DEFAULT_ID = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");

        public static PropietarioCreatorRequest valid() {
            UUID id = java.util.UUID.randomUUID();
            String nombre = "Nombre" + java.util.UUID.randomUUID().toString().substring(0, 8);
            String apellido = "Apellido" + java.util.UUID.randomUUID().toString().substring(0, 8);
            // Genera un DNI aleatorio de 7 u 8 dígitos
            int dniLength = Math.random() < 0.5 ? 7 : 8;
            StringBuilder dniBuilder = new StringBuilder();
            for (int i = 0; i < dniLength; i++) dniBuilder.append((int) (Math.random() * 10));
            String dni = dniBuilder.toString();
            String direccion = "Calle " + java.util.UUID.randomUUID().toString().substring(0, 4) + " 1" + (int)(Math.random()*1000);
            StringBuilder telBuilder = new StringBuilder();
            for (int i = 0; i < 10; i++) telBuilder.append((int) (Math.random() * 10));
            String telefono = telBuilder.toString();
            String email = "usuario" + java.util.UUID.randomUUID().toString().substring(0, 8) + "@example.com";
            return PropietarioCreatorRequest.builder()
                    .id(id)
                    .nombre(nombre)
                    .apellido(apellido)
                    .dni(dni)
                    .direccion(direccion)
                    .telefono(telefono)
                    .email(email)
                    .build();
    }

    public static PropietarioCreatorRequest withoutNombre() {
        return PropietarioCreatorRequest.builder()
                .id(DEFAULT_ID)
                .nombre(null)
                .apellido("Perez")
                .dni("12345678")
                .direccion("Calle Falsa 123")
                .telefono("3814567890")
                .email("juan.perez@email.com")
                .build();
    }

    public static PropietarioCreatorRequest withInvalidEmail() {
        return PropietarioCreatorRequest.builder()
                .id(DEFAULT_ID)
                .nombre("Juan")
                .apellido("Perez")
                .dni("12345678")
                .direccion("Calle Falsa 123")
                .telefono("3814567890")
                .email("email-invalido")
                .build();
    }

    public static PropietarioCreatorRequest withoutId() {
        return PropietarioCreatorRequest.builder()
                .id(null)
                .nombre("Juan")
                .apellido("Perez")
                .dni("12345678")
                .direccion("Calle Falsa 123")
                .telefono("3814567890")
                .email("juan.perez@email.com")
                .build();
    }
}
