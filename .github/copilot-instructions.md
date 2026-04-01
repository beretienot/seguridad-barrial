# .copilot-instructions.md

## Objetivo
Generar código siguiendo Clean Architecture / Hexagonal y Domain-Driven Design (DDD) para la API REST Seguridad Barrial.

## Instrucciones Complementarias
- Para reglas transversales de testing unitario, usar `.github/instructions/testing.instructions.md`.

## Instrucciones Modulares Por Capa
- Para Infrastructure Input, usar `.github/instructions/input-adapter.instructions.md`.
- Para Application, usar `.github/instructions/use-case.instructions.md`.
- Para Domain, usar `.github/instructions/domain.instructions.md`.
- Para Infrastructure Output, usar `.github/instructions/output-adapter.instructions.md`.

Nota: cada instrucción modular incluye su checklist de testing unitario como parte del desarrollo.

## Planificacion Obligatoria
- Antes de implementar cualquier cambio, definir un plan con este formato:
  1. Listar los archivos a crear o modificar, agrupados por capa.
  2. Describir en una línea qué cambia en cada archivo.
  3. Identificar contratos mínimos que la capa actual necesita de la siguiente.
- Ejecutar el trabajo por etapas y marcar cada archivo como completado al terminar.
- Si surge nueva información, ajustar el plan antes de continuar.
- No pasar a la siguiente capa sin validar la etapa actual.

## Capas
- **domain**: entidades de dominio (Aggregates/Entities), Value Objects inmutables con validacion en constructor, y puertos de salida (`OutputPort`). Sin dependencias a frameworks. Aplicar tacticas DDD.
- **application**: interfaces de use case, implementaciones, commands, results y mappers. Cada accion expone un solo metodo publico `perform(...)`.
- **infrastructure/input**: adapters de entrada, REST controllers, DTOs de request/response y mappers de input.
- **infrastructure/output**: adapters de salida, persistencia, entidades JPA, mappers y componentes tecnicos.

## Convenciones de Nombres

### Application
- Interface Use Case: `{Entidad}{Accion}`
- Implementacion Use Case: `{Entidad}{Accion}UseCase`
- Command: `{Entidad}{Accion}Command`
- Result: `{Entidad}{Accion}Result`
- Mapper: `{Entidad}{Accion}UseCaseMapper`

### Domain
- OutputPort: `{Entidad}{Accion}OutputPort`
- Entidad de dominio: nombre simple de negocio (Aggregate Root o Entity)
- Value Object: nombre que representa el concepto de valor (ej: `Dni`, `Email`, `Direccion`)

### Infrastructure Input
- Adapter GET: `{Entidad}{Accion}GetAdapter`
- Adapter POST: `{Entidad}{Accion}PostAdapter`
- Adapter PUT: `{Entidad}{Accion}PutAdapter`
- Adapter DELETE: `{Entidad}{Accion}DeleteAdapter`
- Mapper de input: `{Entidad}{Accion}AdapterMapper`
- Request DTO: `{Entidad}{Accion}Request`
- Response DTO: `{Entidad}{Accion}Response`

### Infrastructure Output
- Output Adapter directo: `{Entidad}{Accion}OutputAdapter`
- Adapter de persistencia: `{Entidad}{Accion}Adapter`
- Mapper de output: `{Entidad}{Accion}OutputAdapterMapper` o `{Entidad}{Accion}AdapterMapper`
- Entidad JPA: `{Entidad}Entity`
- Repositorio: `{Entidad}Repository`

## Reglas Globales
- Mantener separacion estricta por capas.
- No introducir dependencias de infraestructura en domain o application.
- Usar MapStruct para mappers.
- Mantener `perform(...)` como metodo principal de cada accion.
- Seguir las instrucciones modulares especificas segun la capa en la que se este trabajando.
- Los endpoints REST deben seguir convenciones RESTful (nombres de recursos en plural, verbos HTTP correctos, codigos de estado apropiados).
- Respuestas de error en formato JSON consistente.
- Aplicar tacticas DDD: modelar Value Objects inmutables con validacion en constructor, encapsular invariantes en entidades de dominio, y separar identidad (Entities) de valor (Value Objects).
- Todas las propiedades de entidades de dominio deben ser Value Objects; no usar tipos primitivos ni `String` directamente (excepto `UUID id`).
- Usar `UUID` como tipo de identificador por defecto en entidades. Solo usar otro tipo (ej: `Long`) con justificacion explicita.
- El `UUID` de identificacion lo genera y envia el cliente en el request; el servidor no genera IDs.

## Orden de Generacion
1. Infrastructure Input + contratos minimos de Application + tests de la capa.
2. Application + contratos minimos de Domain + tests de la capa.
3. Domain + contratos necesarios para Infrastructure Output + tests de la capa.
4. Infrastructure Output + tests de la capa.

## Flujo Obligatorio Por Capas
1. Trabajar un endpoint (EP) a la vez: completar todas las capas de un EP antes de comenzar el siguiente.
2. Dentro de cada EP, avanzar capa por capa segun el Orden de Generacion.
3. Generar solo la capa actual, sus tests unitarios y los contratos minimos necesarios de la siguiente.
4. No considerar una capa terminada sin sus tests aprobados.
5. Detenerse y pedir revision explicita del usuario antes de continuar a la siguiente capa.
6. Aplicar correcciones si aparecen antes de pasar de capa.
7. No generar multiples capas completas en una sola etapa.
8. No iniciar un nuevo EP hasta que el EP actual este completo en todas sus capas.

## Referencia
- Paquete base: `gov.justucuman.seguridad_barrial`.