package gov.justucuman.seguridad_barrial.domain;

import java.util.Objects;

public class Apellido {

    private final String valor;

    public Apellido(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El apellido es obligatorio");
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
        Apellido apellido = (Apellido) o;
        return valor.equals(apellido.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
