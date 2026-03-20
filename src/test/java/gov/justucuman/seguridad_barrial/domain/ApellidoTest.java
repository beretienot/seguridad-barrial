package gov.justucuman.seguridad_barrial.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApellidoTest {

    @Test
    void shouldCreateApellido_whenValidValue() {
        Apellido apellido = ApellidoMother.valid();

        assertThat(apellido.getValor()).isEqualTo("Perez");
    }

    @Test
    void shouldThrowException_whenValueIsNull() {
        assertThatThrownBy(() -> new Apellido(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El apellido es obligatorio");
    }

    @Test
    void shouldThrowException_whenValueIsBlank() {
        assertThatThrownBy(() -> new Apellido("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El apellido es obligatorio");
    }

    @Test
    void shouldBeEqual_whenSameValor() {
        Apellido apellido1 = ApellidoMother.valid();
        Apellido apellido2 = ApellidoMother.valid();

        assertThat(apellido1).isEqualTo(apellido2);
        assertThat(apellido1.hashCode()).isEqualTo(apellido2.hashCode());
    }
}
