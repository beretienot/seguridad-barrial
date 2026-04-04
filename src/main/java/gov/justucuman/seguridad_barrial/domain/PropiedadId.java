package gov.justucuman.seguridad_barrial.domain;

import java.util.Objects;
import java.util.UUID;

public class PropiedadId {

    private final UUID valor;

    public PropiedadId(UUID valor) {
        if (valor == null) {
            throw new IllegalArgumentException("El propiedadId es obligatorio");
        }
        this.valor = valor;
    }

    public UUID getValor() {
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
        PropiedadId that = (PropiedadId) o;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
