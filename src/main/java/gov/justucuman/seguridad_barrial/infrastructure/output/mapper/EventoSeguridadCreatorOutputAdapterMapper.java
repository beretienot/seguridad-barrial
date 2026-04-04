package gov.justucuman.seguridad_barrial.infrastructure.output.mapper;

import gov.justucuman.seguridad_barrial.domain.EventoSeguridad;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.EventoSeguridadEntity;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropiedadEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventoSeguridadCreatorOutputAdapterMapper {

    @Mapping(target = "id", source = "evento.id")
    @Mapping(target = "propiedad", source = "propiedad")
    @Mapping(target = "tipo", expression = "java(evento.getTipo().getValor())")
    @Mapping(target = "descripcion", expression = "java(evento.getDescripcion().getValor())")
    @Mapping(target = "fechaEvento", expression = "java(evento.getFecha().getValor())")
    EventoSeguridadEntity toEntity(EventoSeguridad evento, PropiedadEntity propiedad);
}
