package gov.justucuman.seguridad_barrial.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DniTest {

    @Test
    void shouldCreateDni_whenValidEightDigits() {
        Dni dni = DniMother.valid();

        assertThat(dni.getValor()).isEqualTo("12345678");
    }

    @Test
    void shouldCreateDni_whenValidSevenDigits() {
        Dni dni = DniMother.withSevenDigits();

        assertThat(dni.getValor()).isEqualTo("1234567");
    }

    @Test
    void shouldThrowException_whenDniIsNull() {
        assertThatThrownBy(() -> new Dni(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El DNI debe tener 7 u 8 digitos");
    }

    @Test
    void shouldThrowException_whenDniHasLetters() {
        assertThatThrownBy(() -> new Dni(DniMother.invalidWithLetters()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El DNI debe tener 7 u 8 digitos");
    }

    @Test
    void shouldThrowException_whenDniHasLessThanSevenDigits() {
        assertThatThrownBy(() -> new Dni(DniMother.invalidWithLessThanSevenDigits()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El DNI debe tener 7 u 8 digitos");
    }

    @Test
    void shouldThrowException_whenDniHasMoreThanEightDigits() {
        assertThatThrownBy(() -> new Dni(DniMother.invalidWithMoreThanEightDigits()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El DNI debe tener 7 u 8 digitos");
    }

    @Test
    void shouldBeEqual_whenSameValor() {
        Dni dni1 = DniMother.valid();
        Dni dni2 = DniMother.valid();

        assertThat(dni1).isEqualTo(dni2);
        assertThat(dni1.hashCode()).isEqualTo(dni2.hashCode());
    }

    @Test
    void shouldNotBeEqual_whenDifferentValor() {
        Dni dni1 = DniMother.valid();
        Dni dni2 = DniMother.withSevenDigits();

        assertThat(dni1).isNotEqualTo(dni2);
    }
}
