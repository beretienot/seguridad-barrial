package gov.justucuman.seguridad_barrial.infrastructure.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PropiedadRepository extends JpaRepository<PropiedadEntity, UUID> {

    List<PropiedadEntity> findByPropietarioId(UUID propietarioId);
}
