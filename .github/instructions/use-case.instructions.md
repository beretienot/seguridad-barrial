---
description: "Use when creating or modifying Application Use Cases, perform methods, commands, results, use case interfaces/implementations, output port contracts, and application mappers. Includes unit testing requirements and requires a short implementation plan before coding."
applyTo: "src/main/java/**/application/**/*.java"
---

# use-case.instructions.md

## Reglas de testing para esta capa
- Los tests cubren exclusivamente el Use Case.
- Se usan reales: mappers de use case, Commands, Results, entidades de dominio y Value Objects.
- Se mockean exclusivamente los OutputPorts: son el único punto de cruce hacia otra capa.
- Los ObjectMother deben generar datos aleatorios por defecto para evitar colisiones. Se permiten variantes explícitas para casos de error o edge cases.

## Objetivo
Definir reglas de implementacion para la capa application de la API REST Seguridad Barrial siguiendo Clean Architecture.

## Alcance
- Interfaces de use case.
- Implementaciones *UseCase.
- Commands y Results.
- Mappers de use case.

## Planificacion Previa (obligatoria)
- Antes de codificar, definir un plan breve de 3-5 pasos.
- Confirmar contrato perform(Command) -> Result.
- Confirmar OutputPort requerido y estrategia de mapeo Command -> Domain -> Result.
- Implementar luego de validar el plan y actualizarlo al terminar cada paso.

## Excepciones de Negocio
- El use case no captura ni transforma excepciones de negocio lanzadas por el dominio o el OutputPort.
- Se propagan tal cual hacia el input adapter, que es el único responsable de traducirlas a respuesta HTTP.

## Reglas Obligatorias
- Interface de use case sin filtro: `{Entidad}{Accion}` (ej: `PropietarioCreator`).
- Interface de use case con filtro: `{Entidad}By{Filtro}{Accion}` (ej: `PropietarioByIdFinder`, `PropietarioByIdUpdater`, `PropietarioByIdDeleter`).
- Implementacion: igual que la interface con sufijo `UseCase` (ej: `PropietarioCreatorUseCase`, `PropietarioByIdFinderUseCase`).
- Componente Spring: @Component (no @Service).
- Inyeccion por constructor con @RequiredArgsConstructor.
- Logging con @Slf4j.
- Un solo metodo publico: `perform(...)`. No agregar metodos publicos auxiliares; la logica adicional va en metodos privados o en el mapper.
- Recibir Command y devolver Result.
- Orquestar dominio y delegar persistencia/externalidades a OutputPort.
- No depender de repositorios JPA ni clases de infraestructura.
- El mapper de use case es responsable de construir **todos** los Value Objects al mapear Command → Domain (ej: `new Dni(command.getDni())`, `new Nombre(command.getNombre())`). Esto garantiza que las validaciones de negocio se ejecuten durante el mapeo, antes de llegar al OutputPort.

## Checklist Rapido
- [ ] Naming de interface/implementacion/command/result correcto.
- [ ] Metodo unico perform(...).
- [ ] Mapper de use case con MapStruct (componentModel = "spring"). Nombre: `{Entidad}{Accion}UseCaseMapper` sin filtro, `{Entidad}By{Filtro}{Accion}UseCaseMapper` con filtro.
- [ ] El mapper construye todos los Value Objects al mapear Command → Domain.
- [ ] Logs de inicio, paso clave y resultado.
- [ ] Dependencias solo a dominio y puertos.

## Ejemplo Minimo
```java
public interface EntidadCreator {
  EntidadCreatorResult perform(EntidadCreatorCommand command);
}

@Component
@Slf4j
@RequiredArgsConstructor
public class EntidadCreatorUseCase implements EntidadCreator {

  private final EntidadCreatorOutputPort outputPort;
  private final EntidadCreatorUseCaseMapper mapper;

  @Override
  public EntidadCreatorResult perform(EntidadCreatorCommand command) {
    Entidad entidad = mapper.toDomain(command);
    entidad = outputPort.perform(entidad);
    return mapper.toResult(entidad);
  }
}
```

## Testing Integrado (obligatorio durante desarrollo)
- Crear test unitario de cada use case implementado.
- Cubrir mínimo:
  - escenario exitoso: verificar que el OutputPort fue invocado con el dominio correcto y que el Result refleja lo devuelto;
  - error de validación de negocio: invariantes del dominio que se disparan al construir Value Objects desde el Command;
  - propagación de excepción del OutputPort: el use case no la suprime ni la transforma indebidamente.
- Estos tests validan de forma indirecta el dominio: si el mapper construye los Value Objects con datos inválidos, el constructor del Value Object falla aquí.
- Para verificar que la transformación Command → Domain es correcta, usar `ArgumentCaptor` para capturar el objeto de dominio que el use case pasó al OutputPort mock y verificar sus propiedades contra el Command original. No construir el objeto de dominio esperado manualmente en el test.

```java
ArgumentCaptor<Entidad> captor = ArgumentCaptor.forClass(Entidad.class);
verify(outputPort).perform(captor.capture());
assertThat(captor.getValue().getDni().getValor()).isEqualTo(command.getDni());
```

## Definition Of Done
- Use Case cumple convenciones de nombre y estructura.
- No contiene detalles de persistencia o framework externo.
- Existe test unitario con casos de exito y error.
- El contrato con OutputPort y el mapeo Command -> Domain -> Result quedan validados por tests.
