package gov.justucuman.seguridad_barrial.domain;

import java.util.Objects;

public class DescripcionEventoSeguridad {

    private final String valor;

    public DescripcionEventoSeguridad(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("La descripcion del evento es obligatoria");
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
        DescripcionEventoSeguridad that = (DescripcionEventoSeguridad) o;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
