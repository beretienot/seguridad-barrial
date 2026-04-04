package gov.justucuman.seguridad_barrial.infrastructure.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventoSeguridadRepository extends JpaRepository<EventoSeguridadEntity, UUID> {
}
