---
description: "Use when creating or modifying Output Adapters, persistence adapters, repository integration, transactional writes, JPA entities mapping, REST/File output clients, and output mappers in infrastructure/output. Includes unit testing requirements and requires a short implementation plan before coding."
applyTo: "src/main/java/**/infrastructure/output/**/*.java"
---

# output-adapter.instructions.md

## Reglas adicionales de testing
- Solo se crean tests unitarios para Adapters (input/output) y Use Cases (application). No se testean directamente entidades de dominio ni value objects salvo edge cases justificados.
- Los ObjectMother deben generar datos aleatorios por defecto para evitar colisiones y mejorar la robustez de los tests. Se permiten variantes explícitas para casos de error o edge cases.

## Objetivo
Definir reglas de implementacion para la capa infrastructure/output de la API REST Seguridad Barrial siguiendo Clean Architecture.

## Alcance
- Adapters directos de salida.
- Adapters de persistencia en infrastructure/output/persistence.
- Mappers de output (Domain <-> Entity/DTO externo).
- Entidades JPA y repositorios asociados.

## Planificacion Previa (obligatoria)
- Antes de codificar, definir un plan breve de 3-5 pasos.
- Confirmar contrato del OutputPort y dependencias tecnicas (repository/client/file manager).
- Confirmar estrategia transaccional y de manejo de errores tecnicos.
- Implementar luego de validar el plan y actualizarlo al terminar cada paso.

## Reglas Obligatorias
- Adapter de salida implementa su OutputPort de dominio.
- Usar @Component y @Slf4j.
- Para escritura, usar @Transactional.
- Inyectar repositorio, mapper y componentes auxiliares necesarios.
- Mantener metodo principal perform(...).
- Delegar persistencia en repositorio y conversion en mapper.
- Evitar logica de negocio compleja en adapter.
- Al mapear Domain -> Entity, desempaquetar **todos** los Value Objects a tipos primitivos (ej: `propietario.getDni().getValor()`, `propietario.getNombre().getValor()`).
- Al mapear Entity -> Domain, reconstruir **todos** los Value Objects desde primitivos (ej: `new Dni(entity.getDni())`, `new Nombre(entity.getNombre())`).

## Checklist Rapido
- [ ] Implementa el OutputPort correcto.
- [ ] Usa @Transactional cuando corresponde.
- [ ] Mapper de output transforma Domain <-> Entity correctamente.
- [ ] Los IDs de tipo UUID se persisten como columna `UUID` o `VARCHAR(36)` en la entidad JPA.
- [ ] Logs relevantes en persistencia/llamadas externas.
- [ ] Manejo consistente de errores tecnicos.

## Ejemplo Minimo
```java
@Component
@Slf4j
@RequiredArgsConstructor
public class EntidadCreatorAdapter implements EntidadCreatorOutputPort {

  private final EntidadRepository repository;
  private final EntidadCreatorOutputAdapterMapper mapper;

  @Override
  @Transactional
  public Entidad perform(Entidad entidad) {
    EntidadEntity entity = mapper.toEntity(entidad);
    entity = repository.save(entity);
    return mapper.toDomain(entity);
  }
}
```

## Testing Integrado (obligatorio durante desarrollo)
- Crear test unitario por adapter de output.
- Si es JPA adapter: mockear solo JpaRepository y usar mapper real.
- Si es REST adapter: mockear solo RestTemplate/WebClient y usar mapper real.
- Cubrir minimo:
  - flujo exitoso de persistencia o llamada externa;
  - error tecnico (repository/client exception);
  - verificacion de datos enviados al borde externo;
  - validacion de transformacion hacia dominio.
- En adapters con archivos, mockear FileManager y verificar operaciones esperadas.

## Definition Of Done
- Output Adapter cumple contrato OutputPort y convenciones de capa.
- La integracion tecnica queda desacoplada del dominio.
- Existe test unitario con casos de exito y falla tecnica.
- El mapping y las interacciones con el borde externo quedan validados por tests.
