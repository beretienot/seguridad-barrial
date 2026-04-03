package gov.justucuman.seguridad_barrial.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class UbicacionGps {

    private final BigDecimal latitud;
    private final BigDecimal longitud;

    public UbicacionGps(BigDecimal latitud, BigDecimal longitud) {
        if (latitud == null) {
            throw new IllegalArgumentException("La latitud es obligatoria");
        }
        if (longitud == null) {
            throw new IllegalArgumentException("La longitud es obligatoria");
        }
        if (latitud.compareTo(BigDecimal.valueOf(-90)) < 0 || latitud.compareTo(BigDecimal.valueOf(90)) > 0) {
            throw new IllegalArgumentException("La latitud debe estar entre -90 y 90");
        }
        if (longitud.compareTo(BigDecimal.valueOf(-180)) < 0 || longitud.compareTo(BigDecimal.valueOf(180)) > 0) {
            throw new IllegalArgumentException("La longitud debe estar entre -180 y 180");
        }
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UbicacionGps that = (UbicacionGps) o;
        return Objects.equals(latitud, that.latitud) && Objects.equals(longitud, that.longitud);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitud, longitud);
    }
}
