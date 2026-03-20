package gov.justucuman.seguridad_barrial.domain;

import java.util.Objects;

public class Direccion {

    private final String valor;

    public Direccion(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("La direccion es obligatoria");
        }
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direccion direccion = (Direccion) o;
        return valor.equals(direccion.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
