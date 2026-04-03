package gov.justucuman.seguridad_barrial.domain;

import java.util.Objects;

public class Provincia {

    private final String valor;

    public Provincia(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("La provincia no puede ser nula ni vacía");
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
        Provincia provincia = (Provincia) o;
        return Objects.equals(valor, provincia.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
