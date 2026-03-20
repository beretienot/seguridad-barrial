package gov.justucuman.seguridad_barrial.domain;

import java.util.Objects;

public class Telefono {

    private final String valor;

    public Telefono(String valor) {
        if (valor != null && valor.isBlank()) {
            throw new IllegalArgumentException("El telefono no puede estar en blanco");
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
        Telefono telefono = (Telefono) o;
        return Objects.equals(valor, telefono.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
