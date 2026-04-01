package gov.justucuman.seguridad_barrial.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @Test
    void shouldCreateEmail_whenValidFormat() {
        Email email = EmailMother.valid();

        assertThat(email.getValor()).matches("^[\\w.+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    @Test
    void shouldCreateEmail_whenValueIsNull() {
        Email email = EmailMother.withNull();

        assertThat(email.getValor()).isNull();
    }

    @Test
    void shouldThrowException_whenInvalidFormat() {
        assertThatThrownBy(() -> new Email(EmailMother.invalidFormat()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El email no tiene un formato valido");
    }

    @Test
    void shouldBeEqual_whenSameValor() {
        String valor = "test@example.com";
        Email email1 = new Email(valor);
        Email email2 = new Email(valor);

        assertThat(email1).isEqualTo(email2);
        assertThat(email1.hashCode()).isEqualTo(email2.hashCode());
    }

    @Test
    void shouldBeEqual_whenBothNull() {
        Email email1 = EmailMother.withNull();
        Email email2 = EmailMother.withNull();

        assertThat(email1).isEqualTo(email2);
        assertThat(email1.hashCode()).isEqualTo(email2.hashCode());
    }
}
