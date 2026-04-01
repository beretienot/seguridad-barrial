---
description: "Use when creating or modifying Input Adapters, REST controllers, request/response DTOs, request mapping, preauthorize, validation, and input mappers in infrastructure/input. Includes unit testing requirements and requires a short implementation plan before coding."
applyTo: "src/main/java/**/infrastructure/input/**/*.java"
---

# input-adapter.instructions.md

## Reglas de testing para esta capa
- Los tests cubren exclusivamente el Input Adapter.
- Todo lo que pertenece a esta capa (mappers de input, DTOs) se usa real.
- Todo lo que cruza hacia otra capa (interfaz del use case) se mockea.
- Los ObjectMother deben generar datos aleatorios por defecto para evitar colisiones. Se permiten variantes explícitas para casos de error o edge cases.

## Convención de Nombres

- GET con filtro: `{Entidad}By{Filtro}GetAdapter` (ej: `PropietarioByIdGetAdapter`, `PropietarioByDniGetAdapter`).
- GET sin filtro (listado): `{Entidad}sGetAdapter` (ej: `PropietariosGetAdapter`).
- POST: `{Entidad}PostAdapter` (ej: `PropietarioPostAdapter`).
- PUT: `{Entidad}By{Filtro}PutAdapter` (ej: `PropietarioByIdPutAdapter`).
- DELETE: `{Entidad}By{Filtro}DeleteAdapter` (ej: `PropietarioByIdDeleteAdapter`).
- El filtro describe el criterio en inglés, consistente con las acciones del resto de capas.
- Mapper de input sin filtro: `{Entidad}{Accion}AdapterMapper` (ej: `PropietarioCreatorAdapterMapper`).
- Mapper de input con filtro: `{Entidad}By{Filtro}{Accion}AdapterMapper` (ej: `PropietarioByIdFinderAdapterMapper`).

## Objetivo
Definir reglas de implementacion para la capa infrastructure/input de la API REST Seguridad Barrial siguiendo Clean Architecture.

## Alcance
- REST controllers y adapters de entrada en infrastructure/input.
- DTOs de request y response en infrastructure/input/dto.
- Mappers de input (Request/Response <-> Command/Result).

## Planificacion Previa (obligatoria)
- Antes de codificar, definir un plan breve de 3-5 pasos.
- Confirmar contrato de entrada: Request DTO, validaciones, ruta HTTP y seguridad.
- Confirmar dependencia al use case interface y formato de respuesta.
- Implementar luego de validar el plan y actualizarlo al terminar cada paso.

## Excepciones de Negocio
- El input adapter es el único lugar donde las excepciones de negocio se traducen a respuesta HTTP.
- Cada excepción relevante tiene su `@ExceptionHandler` en el adapter, con el código de estado y cuerpo JSON apropiados.
- Convención de códigos: `NotFoundException` → 404, `DuplicadoException` → 409, `IllegalArgumentException` de Value Objects → 400.

## Reglas Obligatorias
- Usar @RestController (API REST pura).
- Usar @Slf4j y @RequiredArgsConstructor.
- Definir @RequestMapping base con recurso en plural (ej: `/api/entidades`).
- Aplicar @PreAuthorize con la autoridad correcta cuando se requiera seguridad.
- Un solo metodo publico: `perform(...)`. Cada adapter maneja exactamente un endpoint HTTP; no agregar metodos publicos adicionales.
- Retornar ResponseEntity con el codigo de estado HTTP apropiado.
- Usar @Valid para validacion de Request DTOs.
- Manejar errores con @ExceptionHandler retornando JSON consistente.
- No incluir logica de negocio ni acceso directo a repositorios.

## Convenciones REST
- GET para consulta, POST para creacion, PUT para actualizacion completa, PATCH para parcial, DELETE para eliminacion.
- Codigos de estado: 200 OK, 201 Created, 204 No Content, 400 Bad Request, 404 Not Found, 409 Conflict, 500 Internal Server Error.
- Nombres de recursos en plural y en minuscula con guiones (ej: `/api/alertas-barriales`).
- Respuestas de error en formato JSON con estructura consistente.

## Checklist Rapido
- [ ] Adapter usa @RestController.
- [ ] Seguridad con @PreAuthorize cuando aplique.
- [ ] Request DTO -> Command por mapper.
- [ ] Result -> Response DTO por mapper.
- [ ] Llamado al use case interface (no implementacion concreta).
- [ ] ResponseEntity con codigo de estado correcto.
- [ ] Logs en inicio, pasos clave y errores.

## Ejemplo Minimo
```java
@Slf4j
@RestController
@RequestMapping("/api/entidades")
@RequiredArgsConstructor
public class EntidadCreatorPostAdapter {

  private final EntidadCreator useCase;
  private final EntidadCreatorAdapterMapper mapper;

  @PostMapping
  public ResponseEntity<EntidadCreatorResponse> perform(@Valid @RequestBody EntidadCreatorRequest request) {
    EntidadCreatorCommand command = mapper.toCommand(request);
    EntidadCreatorResult result = useCase.perform(command);
    EntidadCreatorResponse response = mapper.toResponse(result);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
```

## Testing Integrado (obligatorio durante desarrollo)
- Crear test unitario del adapter en el package de test espejo.
- Cubrir mínimo:
  - flujo exitoso: código de estado, body de respuesta y verificación de que el use case fue invocado con los argumentos correctos;
  - validación fallida (400 Bad Request): el adapter rechaza el request sin llegar al use case;
  - excepción del use case: el @ExceptionHandler la convierte en respuesta JSON consistente.
- Para verificar que el Command fue construido correctamente a partir del Request, usar `ArgumentCaptor` para capturar el Command que el adapter pasó al use case mock y verificar sus propiedades contra el Request original. No construir el Command esperado manualmente en el test.

```java
ArgumentCaptor<EntidadCreatorCommand> captor = ArgumentCaptor.forClass(EntidadCreatorCommand.class);
verify(useCase).perform(captor.capture());
assertThat(captor.getValue().getDni()).isEqualTo(request.getDni());
```

## Definition Of Done
- Input Adapter cumple estructura y convenciones REST de capa.
- No contiene logica de dominio.
- Existe test unitario con casos de exito y error.
- El mapeo de entrada/salida y la interaccion con el use case quedan validados por tests.
