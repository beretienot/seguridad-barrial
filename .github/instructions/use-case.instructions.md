---
description: "Use when creating or modifying Application Use Cases, perform methods, commands, results, use case interfaces/implementations, output port contracts, and application mappers. Includes unit testing requirements and requires a short implementation plan before coding."
applyTo: "src/main/java/**/application/**/*.java"
---

# use-case.instructions.md

## Reglas adicionales de testing
- Solo se crean tests unitarios para Adapters (input/output) y Use Cases (application). No se testean directamente entidades de dominio ni value objects salvo edge cases justificados.
- Los ObjectMother deben generar datos aleatorios por defecto para evitar colisiones y mejorar la robustez de los tests. Se permiten variantes explícitas para casos de error o edge cases.

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

## Reglas Obligatorias
- Interface de use case: {Entidad}{Accion}.
- Implementacion: {Entidad}{Accion}UseCase.
- Componente Spring: @Component (no @Service).
- Inyeccion por constructor con @RequiredArgsConstructor.
- Logging con @Slf4j.
- Un solo metodo publico: perform(...).
- Recibir Command y devolver Result.
- Orquestar dominio y delegar persistencia/externalidades a OutputPort.
- No depender de repositorios JPA ni clases de infraestructura.
- Al mapear Command -> Domain, construir **todos** los Value Objects del dominio (ej: `new Dni(command.getDni())`, `new Nombre(command.getNombre())`, etc.) para que las validaciones de negocio se ejecuten en la creacion. Todas las propiedades de la entidad de dominio deben ser Value Objects (excepto `UUID id`).

## Checklist Rapido
- [ ] Naming de interface/implementacion/command/result correcto.
- [ ] Metodo unico perform(...).
- [ ] Mapper de use case con MapStruct (componentModel = "spring").
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
- Mockear solo OutputPorts y configuraciones externas.
- Usar mappers reales del use case.
- Cubrir minimo:
  - escenario exitoso;
  - error de validacion de negocio;
  - propagacion/control de excepcion de output port;
  - verificacion de invocacion al output port con datos esperados.
- Validar que Command -> Domain -> Result se transforma correctamente.
- Incluir en estos tests los casos de dominio necesarios (invariantes y validaciones) cuando se ejecuten a traves del flujo del use case.

## Definition Of Done
- Use Case cumple convenciones de nombre y estructura.
- No contiene detalles de persistencia o framework externo.
- Existe test unitario con casos de exito y error.
- El contrato con OutputPort y el mapeo Command -> Domain -> Result quedan validados por tests.
