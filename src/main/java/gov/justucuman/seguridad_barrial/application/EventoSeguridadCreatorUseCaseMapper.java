package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.DescripcionEventoSeguridad;
import gov.justucuman.seguridad_barrial.domain.EventoSeguridad;
import gov.justucuman.seguridad_barrial.domain.FechaEventoSeguridad;
import gov.justucuman.seguridad_barrial.domain.Propiedad;
import gov.justucuman.seguridad_barrial.domain.PropiedadId;
import gov.justucuman.seguridad_barrial.domain.TipoEventoSeguridad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {
        PropiedadId.class,
        TipoEventoSeguridad.class,
        DescripcionEventoSeguridad.class,
    FechaEventoSeguridad.class,
    LocalDateTime.class
})
public interface EventoSeguridadCreatorUseCaseMapper {

    @Mapping(target = "propiedadId", expression = "java(new PropiedadId(command.getPropiedadId()))")
    @Mapping(target = "tipo", expression = "java(new TipoEventoSeguridad(command.getTipo()))")
    @Mapping(target = "descripcion", expression = "java(new DescripcionEventoSeguridad(command.getDescripcion()))")
    @Mapping(target = "fecha", expression = "java(new FechaEventoSeguridad(LocalDateTime.now().withNano(0)))")
    EventoSeguridad toDomain(EventoSeguridadCreatorCommand command);

    @Mapping(target = "id", expression = "java(evento.getId())")
    @Mapping(target = "propiedadId", expression = "java(evento.getPropiedadId().getValor())")
    @Mapping(target = "tipo", expression = "java(evento.getTipo().getValor())")
    @Mapping(target = "descripcion", expression = "java(evento.getDescripcion().getValor())")
    @Mapping(target = "fecha", expression = "java(evento.getFecha().getValor())")
    @Mapping(target = "latitud", expression = "java(propiedad.getUbicacion() != null ? propiedad.getUbicacion().getLatitud() : null)")
    @Mapping(target = "longitud", expression = "java(propiedad.getUbicacion() != null ? propiedad.getUbicacion().getLongitud() : null)")
    EventoSeguridadCreatorResult toResult(EventoSeguridad evento, Propiedad propiedad);
}
