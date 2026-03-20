package gov.justucuman.seguridad_barrial.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DireccionTest {

    @Test
    void shouldCreateDireccion_whenValidValue() {
        Direccion direccion = DireccionMother.valid();

        assertThat(direccion.getValor()).isEqualTo("Av. Aconquija 1000");
    }

    @Test
    void shouldThrowException_whenValueIsNull() {
        assertThatThrownBy(() -> new Direccion(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La direccion es obligatoria");
    }

    @Test
    void shouldThrowException_whenValueIsBlank() {
        assertThatThrownBy(() -> new Direccion("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La direccion es obligatoria");
    }

    @Test
    void shouldBeEqual_whenSameValor() {
        Direccion direccion1 = DireccionMother.valid();
        Direccion direccion2 = DireccionMother.valid();

        assertThat(direccion1).isEqualTo(direccion2);
        assertThat(direccion1.hashCode()).isEqualTo(direccion2.hashCode());
    }
}
