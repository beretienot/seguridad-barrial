package gov.justucuman.seguridad_barrial.domain;

import java.util.Objects;

public class Localidad {

    private final String valor;

    public Localidad(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("La localidad no puede ser nula ni vacía");
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
        Localidad localidad = (Localidad) o;
        return Objects.equals(valor, localidad.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
