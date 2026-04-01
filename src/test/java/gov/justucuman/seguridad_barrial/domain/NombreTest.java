package gov.justucuman.seguridad_barrial.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NombreTest {

    @Test
    void shouldCreateNombre_whenValidValue() {
        Nombre nombre = NombreMother.valid();

        assertThat(nombre.getValor()).isNotBlank();
    }

    @Test
    void shouldThrowException_whenValueIsNull() {
        assertThatThrownBy(() -> new Nombre(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El nombre es obligatorio");
    }

    @Test
    void shouldThrowException_whenValueIsBlank() {
        assertThatThrownBy(() -> new Nombre("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El nombre es obligatorio");
    }

    @Test
    void shouldBeEqual_whenSameValor() {
        String valor = "Juan";
        Nombre nombre1 = new Nombre(valor);
        Nombre nombre2 = new Nombre(valor);

        assertThat(nombre1).isEqualTo(nombre2);
        assertThat(nombre1.hashCode()).isEqualTo(nombre2.hashCode());
    }
}
