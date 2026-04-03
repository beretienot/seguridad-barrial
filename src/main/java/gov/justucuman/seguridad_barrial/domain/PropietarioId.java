package gov.justucuman.seguridad_barrial.domain;

import java.util.Objects;
import java.util.UUID;

public class PropietarioId {

    private final UUID valor;

    public PropietarioId(UUID valor) {
        if (valor == null) {
            throw new IllegalArgumentException("El propietarioId es obligatorio");
        }
        this.valor = valor;
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropietarioId that = (PropietarioId) o;
        return valor.equals(that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
