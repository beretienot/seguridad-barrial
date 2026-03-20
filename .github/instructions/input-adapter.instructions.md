---
description: "Use when creating or modifying Input Adapters, REST controllers, request/response DTOs, request mapping, preauthorize, validation, and input mappers in infrastructure/input. Includes unit testing requirements and requires a short implementation plan before coding."
applyTo: "src/main/java/**/infrastructure/input/**/*.java"
---

# input-adapter.instructions.md

## Reglas adicionales de testing
- Solo se crean tests unitarios para Adapters (input/output) y Use Cases (application). No se testean directamente entidades de dominio ni value objects salvo edge cases justificados.
- Los ObjectMother deben generar datos aleatorios por defecto para evitar colisiones y mejorar la robustez de los tests. Se permiten variantes explícitas para casos de error o edge cases.

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

## Reglas Obligatorias
- Usar @RestController (API REST pura).
- Usar @Slf4j y @RequiredArgsConstructor.
- Definir @RequestMapping base con recurso en plural (ej: `/api/entidades`).
- Aplicar @PreAuthorize con la autoridad correcta cuando se requiera seguridad.
- El metodo principal debe llamarse perform(...).
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
- Crear test unitario del adapter en el mismo package de test espejo.
- Mockear solo interfaces de use case (dependencias de otra capa).
- Usar mappers de input reales en el test.
- Cubrir minimo:
  - flujo exitoso con codigo de estado y body esperado;
  - validacion fallida (400 Bad Request);
  - excepcion manejada por @ExceptionHandler;
  - verificacion de ResponseEntity y contenido JSON.
- Verificar interaccion con use case (invocacion y argumentos mapeados).

## Definition Of Done
- Input Adapter cumple estructura y convenciones REST de capa.
- No contiene logica de dominio.
- Existe test unitario con casos de exito y error.
- El mapeo de entrada/salida y la interaccion con el use case quedan validados por tests.
