package gov.justucuman.seguridad_barrial.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TelefonoTest {

    @Test
    void shouldCreateTelefono_whenValidValue() {
        Telefono telefono = TelefonoMother.valid();

        assertThat(telefono.getValor()).isNotBlank();
    }

    @Test
    void shouldCreateTelefono_whenValueIsNull() {
        Telefono telefono = TelefonoMother.withNull();

        assertThat(telefono.getValor()).isNull();
    }

    @Test
    void shouldThrowException_whenValueIsBlank() {
        assertThatThrownBy(() -> new Telefono(TelefonoMother.invalidBlank()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El telefono no puede estar en blanco");
    }

    @Test
    void shouldBeEqual_whenSameValor() {
        String valor = "3814001234";
        Telefono telefono1 = new Telefono(valor);
        Telefono telefono2 = new Telefono(valor);

        assertThat(telefono1).isEqualTo(telefono2);
        assertThat(telefono1.hashCode()).isEqualTo(telefono2.hashCode());
    }
}
