package gov.justucuman.seguridad_barrial.infrastructure.output.persistence;

import java.util.UUID;

public class PropietarioEntityMother {

    public static PropietarioEntity valid() {
        UUID id = java.util.UUID.randomUUID();
        String nombre = "Nombre" + java.util.UUID.randomUUID().toString().substring(0, 8);
        String apellido = "Apellido" + java.util.UUID.randomUUID().toString().substring(0, 8);
        int dniLength = Math.random() < 0.5 ? 7 : 8;
        StringBuilder dniBuilder = new StringBuilder();
        for (int i = 0; i < dniLength; i++) dniBuilder.append((int) (Math.random() * 10));
        String dni = dniBuilder.toString();
        String direccion = "Calle " + java.util.UUID.randomUUID().toString().substring(0, 4) + " 1" + (int)(Math.random()*1000);
        StringBuilder telBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) telBuilder.append((int) (Math.random() * 10));
        String telefono = telBuilder.toString();
        String email = "usuario" + java.util.UUID.randomUUID().toString().substring(0, 8) + "@example.com";
        return PropietarioEntity.builder()
                .id(id)
                .nombre(nombre)
                .apellido(apellido)
                .dni(dni)
                .direccion(direccion)
                .telefono(telefono)
                .email(email)
                .build();
    }
}
