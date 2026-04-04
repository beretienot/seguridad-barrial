package gov.justucuman.seguridad_barrial.domain;

import java.util.Objects;

public class TipoEventoSeguridad {

    private final String valor;

    public TipoEventoSeguridad(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El tipo de evento es obligatorio");
        }
        this.valor = valor.trim();
    }

    public String getValor() {
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
        TipoEventoSeguridad that = (TipoEventoSeguridad) o;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
