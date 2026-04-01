package gov.justucuman.seguridad_barrial.domain;


import java.util.UUID;

public interface PropietarioUpdaterOutputPort {
    Propietario findById(UUID id);
    void update(Propietario propietario);
}
