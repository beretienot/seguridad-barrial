package gov.justucuman.seguridad_barrial.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @Test
    void shouldCreateEmail_whenValidFormat() {
        Email email = EmailMother.valid();

        assertThat(email.getValor()).isEqualTo("juan@example.com");
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
        Email email1 = EmailMother.valid();
        Email email2 = EmailMother.valid();

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
