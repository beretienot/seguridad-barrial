package gov.justucuman.seguridad_barrial.domain;

import java.util.Objects;

public class Nombre {

    private final String valor;

    public Nombre(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
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
        Nombre nombre = (Nombre) o;
        return valor.equals(nombre.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
