---
description: "Use when creating, improving, or reviewing unit tests in this repository. Covers transversal testing conventions with JUnit 5, Mockito, AssertJ, test structure, naming, and mocking rules for Clean Architecture."
applyTo: "src/test/java/**/*.java"
---

# testing.instructions.md

## Reglas de testing transversales
- En el test de cada capa: se usan reales todas las clases que pertenecen a esa capa, y se mockea todo lo que cruza hacia otra capa.
- Los objetos de dominio y Value Objects nunca se mockean: son modelos puros y siempre se instancian reales.
- Los ObjectMother deben generar datos aleatorios por defecto para evitar colisiones. Se permiten variantes explícitas para casos de error o edge cases.

## Objetivo
Definir reglas transversales para test unitarios de la API REST Seguridad Barrial siguiendo Clean Architecture / Hexagonal.

## Alcance
- Convenciones comunes para tests de todas las capas.
- Estructura de paquetes de test.
- Naming de tests.
- Filosofia de mocking y uso de contexto Spring.
- Reglas de Object Mothers y datos de prueba.

## Regla de Reparto
- Lo especifico de testing para Input Adapters se define en `.github/instructions/input-adapter.instructions.md`.
- Lo especifico de testing para Use Cases se define en `.github/instructions/use-case.instructions.md`.
- Lo especifico de testing para Output Adapters se define en `.github/instructions/output-adapter.instructions.md`.
- Este archivo conserva solo reglas generales compartidas.

## Stack de Testing
- JUnit 5 como framework principal.
- Mockito para mocks de dependencias externas.
- AssertJ para assertions expresivas.
- Spring test slices cuando el contexto minimo sea necesario.
- Evitar `@SpringBootTest` salvo tests de integracion reales.

## Principios Generales
- Cada test debe ser independiente y ejecutable por separado.
- Priorizar tests rapidos con el menor contexto posible.
- Probar comportamiento observable, no detalles internos triviales.
- Cubrir casos exitosos, casos de error y edge cases relevantes.
- Los mappers se prueban realmente dentro de los tests de las capas que los usan; no crear tests aislados salvo necesidad justificada.

## Convenciones de Nombres
- Clases de test: `{ClaseOriginal}Test`.
- Metodos de test: `should{ResultadoEsperado}_when{Condicion}`.

Ejemplos:
- `shouldReturnDocument_whenValidCommandProvided()`
- `shouldThrowException_whenInvalidDataProvided()`
- `shouldRedirectWithSuccessMessage_whenFormIsValid()`

## Estructura de Paquetes
- Mantener en `src/test/java` la misma estructura de paquetes que en `src/main/java`.
- Ubicar cada test en el package espejo de la clase que prueba.
- Ubicar Object Mothers en el mismo package espejo de la clase de datos correspondiente.

## Object Mothers
- Crear Object Mothers para DTOs, Commands, Results, Entities, Requests, Responses y Value Objects de dominio.
- No crear Object Mothers para Use Cases, Adapters, Services, Mappers ni Repositories.
- Los Object Mothers deben ofrecer variantes legibles para happy path, error path y edge cases comunes.
- Para Value Objects, incluir variantes con datos validos e invalidos (ej: `DniMother.valid()`, `DniMother.withInvalidFormat()`).

## Contexto Spring vs Mockito Puro
- Empezar por Mockito puro cuando la logica no requiere infraestructura Spring.
- Usar test slices especificos cuando sea necesario validar binding, seguridad, configuracion, cliente HTTP o transacciones.
- Elegir el contexto mas chico posible.

## Cobertura Minima Esperada
- Happy path.
- Error path relevante.
- Edge case relevante cuando exista una regla o validacion no trivial.
- Verificaciones de colaboracion solo cuando aporten valor al comportamiento esperado.

## Definition Of Done
- El test esta en el package correcto.
- El nombre del test describe escenario y resultado.
- Los mocks se limitan a los bordes necesarios.
- Las assertions verifican comportamiento observable.
- La cobertura minima de happy path y error path esta presente.