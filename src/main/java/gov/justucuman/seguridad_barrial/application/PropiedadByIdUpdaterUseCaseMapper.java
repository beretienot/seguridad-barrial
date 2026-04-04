package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Direccion;
import gov.justucuman.seguridad_barrial.domain.Localidad;
import gov.justucuman.seguridad_barrial.domain.Propiedad;
import gov.justucuman.seguridad_barrial.domain.Provincia;
import gov.justucuman.seguridad_barrial.domain.UbicacionGps;

public class PropiedadByIdUpdaterUseCaseMapper {

    public Propiedad applyUpdate(PropiedadByIdUpdaterCommand command, Propiedad existing) {
        Direccion direccion = command.getDireccion() != null ? new Direccion(command.getDireccion()) : null;
        Provincia provincia = command.getProvincia() != null ? new Provincia(command.getProvincia()) : null;
        Localidad localidad = command.getLocalidad() != null ? new Localidad(command.getLocalidad()) : null;
        UbicacionGps ubicacion = command.getLatitud() != null && command.getLongitud() != null
                ? new UbicacionGps(command.getLatitud(), command.getLongitud()) : null;
        return existing.update(direccion, provincia, localidad, ubicacion);
    }
}
