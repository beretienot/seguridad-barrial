package gov.justucuman.seguridad_barrial.domain;

import java.util.Objects;

public class Dni {

    private final String valor;

    public Dni(String valor) {
        if (valor == null || !valor.matches("\\d{7,8}")) {
            throw new IllegalArgumentException("El DNI debe tener 7 u 8 digitos");
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
        Dni dni = (Dni) o;
        return valor.equals(dni.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
