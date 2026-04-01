package gov.justucuman.seguridad_barrial.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PropietarioTest {

    @Test
    void shouldCreatePropietario_whenAllFieldsProvided() {
        Propietario propietario = PropietarioMother.valid();

        assertThat(propietario.getId()).isNotNull();
        assertThat(propietario.getNombre()).isNotNull();
        assertThat(propietario.getApellido()).isNotNull();
        assertThat(propietario.getDni()).isNotNull();
        assertThat(propietario.getDireccion()).isNotNull();
        assertThat(propietario.getTelefono()).isNotNull();
        assertThat(propietario.getEmail()).isNotNull();
    }

    @Test
    void shouldCreatePropietario_whenOptionalFieldsAreNull() {
        Propietario propietario = PropietarioMother.withoutOptionalFields();

        assertThat(propietario.getTelefono()).isNull();
        assertThat(propietario.getEmail()).isNull();
    }

    @Test
    void shouldThrowException_whenIdIsNull() {
        assertThatThrownBy(() -> new Propietario(
                null,
                NombreMother.valid(),
                ApellidoMother.valid(),
                DniMother.valid(),
                DireccionMother.valid(),
                TelefonoMother.valid(),
                EmailMother.valid()
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El id es obligatorio");
    }

    @Test
    void shouldThrowException_whenNombreIsNull() {
        assertThatThrownBy(() -> new Propietario(
                UUID.randomUUID(),
                null,
                ApellidoMother.valid(),
                DniMother.valid(),
                DireccionMother.valid(),
                TelefonoMother.valid(),
                EmailMother.valid()
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El nombre es obligatorio");
    }

    @Test
    void shouldThrowException_whenApellidoIsNull() {
        assertThatThrownBy(() -> new Propietario(
                UUID.randomUUID(),
                NombreMother.valid(),
                null,
                DniMother.valid(),
                DireccionMother.valid(),
                TelefonoMother.valid(),
                EmailMother.valid()
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El apellido es obligatorio");
    }

    @Test
    void shouldThrowException_whenDniIsNull() {
        assertThatThrownBy(() -> new Propietario(
                UUID.randomUUID(),
                NombreMother.valid(),
                ApellidoMother.valid(),
                null,
                DireccionMother.valid(),
                TelefonoMother.valid(),
                EmailMother.valid()
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El DNI es obligatorio");
    }

    @Test
    void shouldThrowException_whenDireccionIsNull() {
        assertThatThrownBy(() -> new Propietario(
                UUID.randomUUID(),
                NombreMother.valid(),
                ApellidoMother.valid(),
                DniMother.valid(),
                null,
                TelefonoMother.valid(),
                EmailMother.valid()
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La direccion es obligatoria");
    }
}
