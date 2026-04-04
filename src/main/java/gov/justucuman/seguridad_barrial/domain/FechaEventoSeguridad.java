package gov.justucuman.seguridad_barrial.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class FechaEventoSeguridad {

    private final LocalDateTime valor;

    public FechaEventoSeguridad(LocalDateTime valor) {
        if (valor == null) {
            throw new IllegalArgumentException("La fecha del evento es obligatoria");
        }
        this.valor = valor;
    }

    public LocalDateTime getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FechaEventoSeguridad that = (FechaEventoSeguridad) o;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
