package gov.justucuman.seguridad_barrial.infrastructure.config;

import gov.justucuman.seguridad_barrial.application.EventoSeguridadCreator;
import gov.justucuman.seguridad_barrial.application.EventoSeguridadCreatorUseCase;
import gov.justucuman.seguridad_barrial.application.EventoSeguridadCreatorUseCaseMapper;
import gov.justucuman.seguridad_barrial.application.PropiedadByIdDeleter;
import gov.justucuman.seguridad_barrial.application.PropiedadByIdDeleterUseCase;
import gov.justucuman.seguridad_barrial.application.PropiedadByIdFinder;
import gov.justucuman.seguridad_barrial.application.PropiedadByIdFinderUseCase;
import gov.justucuman.seguridad_barrial.application.PropiedadByIdFinderUseCaseMapper;
import gov.justucuman.seguridad_barrial.application.PropiedadByIdUpdater;
import gov.justucuman.seguridad_barrial.application.PropiedadByIdUpdaterUseCase;
import gov.justucuman.seguridad_barrial.application.PropiedadByIdUpdaterUseCaseMapper;
import gov.justucuman.seguridad_barrial.application.PropiedadByPropietarioIdFinder;
import gov.justucuman.seguridad_barrial.application.PropiedadByPropietarioIdFinderUseCase;
import gov.justucuman.seguridad_barrial.application.PropiedadCreator;
import gov.justucuman.seguridad_barrial.application.PropiedadCreatorUseCase;
import gov.justucuman.seguridad_barrial.application.PropiedadCreatorUseCaseMapper;
import gov.justucuman.seguridad_barrial.application.PropietarioByIdDeleter;
import gov.justucuman.seguridad_barrial.application.PropietarioByIdDeleterUseCase;
import gov.justucuman.seguridad_barrial.application.PropietarioByIdFinder;
import gov.justucuman.seguridad_barrial.application.PropietarioByIdFinderUseCase;
import gov.justucuman.seguridad_barrial.application.PropietarioByIdFinderUseCaseMapper;
import gov.justucuman.seguridad_barrial.application.PropietarioByIdUpdater;
import gov.justucuman.seguridad_barrial.application.PropietarioByIdUpdaterUseCase;
import gov.justucuman.seguridad_barrial.application.PropietarioByIdUpdaterUseCaseMapper;
import gov.justucuman.seguridad_barrial.application.PropietarioCreator;
import gov.justucuman.seguridad_barrial.application.PropietarioCreatorUseCase;
import gov.justucuman.seguridad_barrial.application.PropietarioCreatorUseCaseMapper;
import gov.justucuman.seguridad_barrial.application.PropietariosFinder;
import gov.justucuman.seguridad_barrial.application.PropietariosFinderUseCase;
import gov.justucuman.seguridad_barrial.domain.EventoSeguridadCreatorOutputPort;
import gov.justucuman.seguridad_barrial.domain.EventoSeguridadNotifierOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropiedadByIdDeleterOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropiedadByIdFinderOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropiedadByIdUpdaterOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropiedadByPropietarioIdFinderOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropiedadCreatorOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropietarioByIdDeleterOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropietarioByIdFinderOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropietarioByIdUpdaterOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropietarioCreatorOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropietariosFinderOutputPort;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public EventoSeguridadCreatorUseCaseMapper eventoSeguridadCreatorUseCaseMapper() {
        return Mappers.getMapper(EventoSeguridadCreatorUseCaseMapper.class);
    }

    @Bean
    public PropiedadByIdFinderUseCaseMapper propiedadByIdFinderUseCaseMapper() {
        return Mappers.getMapper(PropiedadByIdFinderUseCaseMapper.class);
    }

    @Bean
    public PropiedadByIdUpdaterUseCaseMapper propiedadByIdUpdaterUseCaseMapper() {
        return new PropiedadByIdUpdaterUseCaseMapper();
    }

    @Bean
    public PropiedadCreatorUseCaseMapper propiedadCreatorUseCaseMapper() {
        return Mappers.getMapper(PropiedadCreatorUseCaseMapper.class);
    }

    @Bean
    public PropietarioByIdFinderUseCaseMapper propietarioByIdFinderUseCaseMapper() {
        return Mappers.getMapper(PropietarioByIdFinderUseCaseMapper.class);
    }

    @Bean
    public PropietarioByIdUpdaterUseCaseMapper propietarioByIdUpdaterUseCaseMapper() {
        return new PropietarioByIdUpdaterUseCaseMapper();
    }

    @Bean
    public PropietarioCreatorUseCaseMapper propietarioCreatorUseCaseMapper() {
        return Mappers.getMapper(PropietarioCreatorUseCaseMapper.class);
    }

    @Bean
    public EventoSeguridadCreator eventoSeguridadCreator(
            PropiedadByIdFinderOutputPort propiedadFinderPort,
            EventoSeguridadCreatorOutputPort outputPort,
            EventoSeguridadNotifierOutputPort notifierOutputPort,
            EventoSeguridadCreatorUseCaseMapper mapper
    ) {
        return new EventoSeguridadCreatorUseCase(propiedadFinderPort, outputPort, notifierOutputPort, mapper);
    }

    @Bean
    public PropiedadByIdDeleter propiedadByIdDeleter(PropiedadByIdDeleterOutputPort outputPort) {
        return new PropiedadByIdDeleterUseCase(outputPort);
    }

    @Bean
    public PropiedadByIdFinder propiedadByIdFinder(
            PropiedadByIdFinderOutputPort outputPort,
            PropiedadByIdFinderUseCaseMapper mapper
    ) {
        return new PropiedadByIdFinderUseCase(outputPort, mapper);
    }

    @Bean
    public PropiedadByIdUpdater propiedadByIdUpdater(
            PropiedadByIdFinderOutputPort finderPort,
            PropiedadByIdUpdaterOutputPort updaterPort,
            PropiedadByIdUpdaterUseCaseMapper mapper
    ) {
        return new PropiedadByIdUpdaterUseCase(finderPort, updaterPort, mapper);
    }

    @Bean
    public PropiedadByPropietarioIdFinder propiedadByPropietarioIdFinder(
            PropietarioByIdFinderOutputPort propietarioFinderPort,
            PropiedadByPropietarioIdFinderOutputPort outputPort,
            PropiedadByIdFinderUseCaseMapper mapper
    ) {
        return new PropiedadByPropietarioIdFinderUseCase(propietarioFinderPort, outputPort, mapper);
    }

    @Bean
    public PropiedadCreator propiedadCreator(
            PropietarioByIdFinderOutputPort propietarioFinderPort,
            PropiedadCreatorOutputPort outputPort,
            PropiedadCreatorUseCaseMapper mapper
    ) {
        return new PropiedadCreatorUseCase(propietarioFinderPort, outputPort, mapper);
    }

    @Bean
    public PropietarioByIdDeleter propietarioByIdDeleter(PropietarioByIdDeleterOutputPort outputPort) {
        return new PropietarioByIdDeleterUseCase(outputPort);
    }

    @Bean
    public PropietarioByIdFinder propietarioByIdFinder(
            PropietarioByIdFinderOutputPort outputPort,
            PropietarioByIdFinderUseCaseMapper mapper
    ) {
        return new PropietarioByIdFinderUseCase(outputPort, mapper);
    }

    @Bean
    public PropietarioByIdUpdater propietarioByIdUpdater(
            PropietarioByIdFinderOutputPort finderPort,
            PropietarioByIdUpdaterOutputPort updaterPort,
            PropietarioByIdUpdaterUseCaseMapper mapper
    ) {
        return new PropietarioByIdUpdaterUseCase(finderPort, updaterPort, mapper);
    }

    @Bean
    public PropietarioCreator propietarioCreator(
            PropietarioCreatorOutputPort outputPort,
            PropietarioCreatorUseCaseMapper mapper
    ) {
        return new PropietarioCreatorUseCase(outputPort, mapper);
    }

    @Bean
    public PropietariosFinder propietariosFinder(
            PropietariosFinderOutputPort outputPort,
            PropietarioByIdFinderUseCaseMapper mapper
    ) {
        return new PropietariosFinderUseCase(outputPort, mapper);
    }
}
