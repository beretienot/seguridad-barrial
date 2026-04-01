---
description: "Use when creating or modifying Output Adapters, persistence adapters, repository integration, transactional writes, JPA entities mapping, REST/File output clients, and output mappers in infrastructure/output. Includes unit testing requirements and requires a short implementation plan before coding."
applyTo: "src/main/java/**/infrastructure/output/**/*.java"
---

# output-adapter.instructions.md

## Reglas de testing para esta capa
- Los tests cubren exclusivamente el Output Adapter.
- Todo lo que pertenece a esta capa (mappers de output, entidades JPA) se usa real.
- Todo lo que cruza hacia otra capa (JpaRepository, RestTemplate, clientes externos) se mockea.
- Los ObjectMother deben generar datos aleatorios por defecto para evitar colisiones. Se permiten variantes explícitas para casos de error o edge cases.

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

## Excepciones
- El output adapter es el único lugar que conoce excepciones técnicas de infraestructura (ej: `DataIntegrityViolationException`).
- Cuando una excepción técnica representa una violación de negocio conocida, el adapter la captura y la traduce a la excepción de dominio correspondiente (ej: `DataIntegrityViolationException` por DNI duplicado → `DniDuplicadoException`).
- Las excepciones técnicas sin semántica de negocio se dejan propagar tal cual.

## Reglas Obligatorias
- Adapter de salida implementa su OutputPort de dominio.
- Usar @Component y @Slf4j.
- Para escritura, usar @Transactional.
- Inyectar repositorio, mapper y componentes auxiliares necesarios.
- Implementar exactamente los metodos definidos por el OutputPort correspondiente, sin agregar metodos publicos propios. Si el OutputPort define un unico metodo de escritura, nombrarlo `perform(...)`; si define metodos de consulta o multiples operaciones, respetar los nombres del contrato del puerto.
- Naming del adapter sin filtro: `{Entidad}{Accion}Adapter` (ej: `PropietarioCreatorAdapter`).
- Naming del adapter con filtro: `{Entidad}By{Filtro}{Accion}Adapter` (ej: `PropietarioByIdFinderAdapter`, `PropietarioByIdDeleterAdapter`).
- Mapper de output sin filtro: `{Entidad}{Accion}OutputAdapterMapper` (ej: `PropietarioCreatorOutputAdapterMapper`).
- Mapper de output con filtro: `{Entidad}By{Filtro}{Accion}OutputAdapterMapper` (ej: `PropietarioByIdFinderOutputAdapterMapper`).
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
    try {
      EntidadEntity entity = mapper.toEntity(entidad);
      entity = repository.save(entity);
      return mapper.toDomain(entity);
    } catch (DataIntegrityViolationException e) {
      // Traducir excepción técnica conocida a excepción de negocio.
      throw new DniDuplicadoException("Ya existe una entidad con ese DNI");
    }
  }
}
```

## Testing Integrado (obligatorio durante desarrollo)
- Crear test unitario por adapter de output.
- Cubrir mínimo:
  - flujo exitoso: verificar que el objeto enviado al borde externo tiene los valores correctos (Value Objects desempaquetados) y que el dominio devuelto está reconstruido correctamente;
  - error técnico conocido: excepción técnica del repositorio se traduce a la excepción de negocio correspondiente;
  - error técnico desconocido: excepción sin semántica de negocio se propaga sin ser suprimida;
  - en adapters con archivos u otros componentes auxiliares, mockearlos y verificar las operaciones esperadas.
- Para verificar que el mapeo Domain → Entity es correcto (Value Objects desempaquetados), usar `ArgumentCaptor` para capturar la Entity que el adapter pasó al repositorio mock y verificar sus campos primitivos contra el objeto de dominio original. No construir la Entity esperada manualmente en el test.

```java
ArgumentCaptor<EntidadEntity> captor = ArgumentCaptor.forClass(EntidadEntity.class);
verify(repository).save(captor.capture());
assertThat(captor.getValue().getDni()).isEqualTo(dominio.getDni().getValor());
```

## Definition Of Done
- Output Adapter cumple contrato OutputPort y convenciones de capa.
- La integracion tecnica queda desacoplada del dominio.
- Existe test unitario con casos de exito y falla tecnica.
- El mapping y las interacciones con el borde externo quedan validados por tests.
