---
description: "Use when reviewing merge requests or pull requests in this repository, focusing on Clean Architecture and Portal Personal conventions."
---

# mr-review.instructions.md

## Objetivo
Revisar Merge Requests (MRs) asegurando que el código siga Clean Architecture / Hexagonal según las convenciones del Portal Personal.

## Checklist de Revisión por Capas

### 1. Estructura General del MR
- [ ] El MR tiene una descripción clara del cambio y su propósito
- [ ] Los commits son atómicos y tienen mensajes descriptivos
- [ ] No hay archivos no relacionados con el objetivo del MR
- [ ] Los cambios siguen el orden de generación: Input → Application → Domain → Output
- [ ] No hay código comentado sin justificación
- [ ] No hay TODOs sin ticket asociado

### 2. Domain Layer (Entidades y Puertos de Salida)

#### Entidades de Dominio
- [ ] Las entidades NO tienen dependencias a frameworks (Spring, JPA, etc.)
- [ ] Los nombres son claros y reflejan conceptos del negocio
- [ ] No contienen lógica de persistencia ni anotaciones JPA
- [ ] Usan Value Objects cuando sea apropiado
- [ ] Implementan validaciones de negocio cuando sea necesario

#### OutputPorts
- [ ] Las interfaces siguen el patrón: `{Entidad}{Acción}OutputPort`
- [ ] Definen contratos claros sin dependencias técnicas
- [ ] Un puerto por operación (Single Responsibility)
- [ ] Los nombres de métodos son descriptivos (preferible `perform(...)`)

**Ejemplo Correcto:**
```java
package org.justucuman.portalpersonal.{modulo}.domain;

public interface EntidadCreatorOutputPort {
    Entidad perform(Entidad entidad);
}
```

**Errores Comunes:**
- ❌ Anotaciones Spring en interfaces de dominio
- ❌ Dependencias a clases de infraestructura
- ❌ Múltiples responsabilidades en un puerto

### 3. Application Layer (Use Cases)

#### Interfaces de Use Case
- [ ] Nombre: `{Entidad}{Acción}` (e.g., `EmpleadoCreator`, `TramiteFinder`)
- [ ] Ubicadas en package `application`
- [ ] Un solo método público: `perform(...)`
- [ ] Reciben Commands y retornan Results

#### Implementaciones de Use Case
- [ ] Nombre: `{Entidad}{Acción}UseCase`
- [ ] Usan `@Component` (NO `@Service`)
- [ ] Usan `@RequiredArgsConstructor` para inyección
- [ ] Incluyen `@Slf4j` para logging
- [ ] Inyectan OutputPort y Mapper correspondientes
- [ ] Logs informativos: inicio, pasos clave, resultado
- [ ] Lógica de negocio clara y sin dependencias técnicas
- [ ] Delegación correcta al OutputPort

#### Commands y Results
- [ ] Nombres: `{Entidad}{Acción}Command` / `{Entidad}{Acción}Result`
- [ ] Solo contienen datos (DTOs)
- [ ] Usan Lombok apropiadamente (`@Data`, `@Builder`, etc.)
- [ ] No contienen lógica de negocio

#### Mappers de Use Case
- [ ] Nombre: `{Entidad}{Acción}UseCaseMapper`
- [ ] Usan MapStruct: `@Mapper(componentModel = "spring")`
- [ ] Métodos: `toDomain(Command)`, `toResult(Domain)`, etc.
- [ ] Usan `@Mapping` y `@Named` correctamente para referencias circulares

**Ejemplo Correcto:**
```java
@RequiredArgsConstructor
@Component
@Slf4j
public class EntidadCreatorUseCase implements EntidadCreator {

    private final EntidadCreatorOutputPort outputPort;
    private final EntidadCreatorUseCaseMapper mapper;

    @Override
    public EntidadCreatorResult perform(EntidadCreatorCommand command) {
        log.info("Iniciando creación de entidad con título: {}", command.getTitulo());

        Entidad entidad = mapper.toDomain(command);
        entidad = outputPort.perform(entidad);
        log.debug("Entidad procesada por outputPort");

        EntidadCreatorResult result = mapper.toResult(entidad);
        log.info("Entidad creada exitosamente con ID: {}", result.getId());

        return result;
    }
}
```

**Errores Comunes:**
- ❌ Lógica de persistencia en el use case
- ❌ Uso de `@Service` en lugar de `@Component`
- ❌ Falta de logs informativos
- ❌ Múltiples métodos públicos (debe haber solo `perform`)
- ❌ Dependencias directas a repositorios o entidades JPA

### 4. Infrastructure Input (Controllers/Adapters)

#### Controllers
- [ ] Usan `@Controller` (NO `@RestController` a menos que sea API REST pura)
- [ ] Extienden `AbstractController`
- [ ] Incluyen `@Slf4j`, `@RequiredArgsConstructor`
- [ ] `@RequestMapping` a nivel de clase con path base
- [ ] `@PreAuthorize("hasAuthority('...')")` para seguridad
- [ ] `@SessionAttributes` cuando sea necesario
- [ ] Método principal llamado `perform(...)`
- [ ] Usan `RedirectAttributes` para mensajes flash
- [ ] Manejan excepciones con `@ExceptionHandler`
- [ ] Logs al inicio, pasos clave, warnings/errors en excepciones

#### ModelViews (DTOs de Input)
- [ ] Nombre: `{Entidad}ModelView`
- [ ] Ubicados en `infrastructure/input/model`
- [ ] Usan validaciones de Bean Validation (`@NotNull`, `@NotBlank`, etc.)
- [ ] Solo contienen datos necesarios para la vista

#### Mappers de Input
- [ ] Nombre: `{Entidad}{Acción}AdapterMapper` o `{Entidad}PostAdapterMapper`
- [ ] Mapean ModelView ↔ Command y Result ↔ ModelView
- [ ] Usan MapStruct correctamente

**Ejemplo Correcto:**
```java
@Slf4j
@Controller
@SessionAttributes({ "entidad", "relacionModel" })
@RequestMapping("/modulo")
@PreAuthorize("hasAuthority('usuarioModulo')")
@RequiredArgsConstructor
public class EntidadFormPostAdapter extends AbstractController {

    private final EntidadCreator useCase;
    private final EntidadPostAdapterMapper mapper;

    @PostMapping("/form-entidad")
    public String perform(@Valid @ModelAttribute EntidadModelView entidad,
                          @RequestParam("relacionId") Integer relacionId,
                          RedirectAttributes flash) throws IOException {

        log.info("Procesando formulario de entidad: {}", entidad);

        // ... lógica del adapter ...

        flash.addFlashAttribute("success", "Entidad guardada correctamente");
        return "redirect:/modulo/listar-entidades";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, RedirectAttributes redirectAttributes) {
        log.warn("Error de validación: {}", ex.getMessage());
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/modulo/form-entidad";
    }
}
```

**Errores Comunes:**
- ❌ Lógica de negocio en el controller
- ❌ Llamadas directas a repositorios
- ❌ Falta de validaciones en ModelViews
- ❌ No usar RedirectAttributes para mensajes
- ❌ Falta de manejo de excepciones
- ❌ No extender AbstractController

### 5. Infrastructure Output (Adapters de Salida)

#### Adapters Directos
- [ ] Nombre: `{Entidad}{Acción}OutputAdapter`
- [ ] Usan `@Component` + `@AllArgsConstructor` o `@RequiredArgsConstructor`
- [ ] Incluyen `@Slf4j`
- [ ] Implementan el OutputPort correspondiente
- [ ] Método principal: `perform(...)`

#### Adapters de Persistencia
- [ ] Ubicados en `infrastructure/output/persistence`
- [ ] Implementan la interfaz del OutputPort
- [ ] Inyectan Repository, Mapper y componentes auxiliares
- [ ] Usan `@Transactional` en operaciones de escritura
- [ ] Logs informativos de las operaciones de persistencia
- [ ] Delegan correctamente en el repositorio

#### Entidades JPA
- [ ] Nombre: `{Entidad}Entity`
- [ ] Ubicadas en `infrastructure/output/persistence/model`
- [ ] Usan `@Entity`, `@Table`
- [ ] Lombok: `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`
- [ ] Implementan `Serializable`
- [ ] Relaciones JPA bien definidas (`@ManyToOne`, `@OneToMany`, etc.)
- [ ] Evitan referencias circulares en `toString()` (usar `@ToString.Exclude`)

#### Repositorios
- [ ] Nombre: `{Entidad}Repository`
- [ ] Extienden `JpaRepository<Entity, ID>`
- [ ] Ubicados en `infrastructure/output/persistence`
- [ ] Queries personalizadas bien nombradas

#### Mappers de Output
- [ ] Nombre: `{Entidad}{Acción}OutputAdapterMapper` o `{Entidad}{Acción}AdapterMapper`
- [ ] Mapean Entity ↔ Domain
- [ ] Manejan correctamente referencias circulares con `@Named`

**Ejemplo Correcto:**
```java
@Component
@AllArgsConstructor
@Slf4j
public class EntidadCreatorAdapter implements EntidadCreatorOutputPort {

    EntidadRepository repository;
    EntidadCreatorOutputPortAdapterMapper mapper;
    FileManager fileManager;

    @Transactional
    @Override
    public Entidad perform(Entidad entidad) {
        log.info("Persistiendo entidad con título: {}", entidad.getTitulo());

        String uuidFileName = UUID.randomUUID().toString() + ".pdf";
        
        EntidadEntity entidadEntity = mapper.toEntity(entidad);
        entidadEntity.setFileName(uuidFileName);

        entidadEntity = repository.save(entidadEntity);
        log.info("EntidadEntity persistida con ID: {}", entidadEntity.getId());

        fileManager.save(entidad.getContenido(), "pdf", uuidFileName);

        return mapper.toDomain(entidadEntity);
    }
}
```

**Errores Comunes:**
- ❌ Lógica de negocio en el adapter
- ❌ No usar `@Transactional` en operaciones de escritura
- ❌ Referencias circulares en entidades JPA
- ❌ Falta de logs en operaciones críticas
- ❌ Mapeo manual en lugar de usar MapStruct

### 6. Testing

#### Tests Unitarios
- [ ] Cada use case tiene tests
- [ ] Se mockean las dependencias (OutputPort, Mappers)
- [ ] Se prueban casos exitosos y casos de error
- [ ] Nombres descriptivos de tests: `should{Acción}When{Condición}`
- [ ] Usan JUnit 5 y Mockito

#### Tests de Integración
- [ ] Controllers tienen tests de integración
- [ ] Se usan `@SpringBootTest` o `@WebMvcTest` apropiadamente
- [ ] Se prueban validaciones y manejo de errores
- [ ] Se verifica el comportamiento end-to-end

**Errores Comunes:**
- ❌ Falta de tests para nuevas funcionalidades
- ❌ Tests que dependen de bases de datos reales sin limpiar estado
- ❌ Aserciones débiles (solo verificar que no falla)

### 7. Seguridad

- [ ] Endpoints protegidos con `@PreAuthorize`
- [ ] Validación de permisos correcta
- [ ] No hay exposición de información sensible en logs
- [ ] Validaciones de entrada implementadas
- [ ] Manejo seguro de archivos (si aplica)

### 8. Performance y Buenas Prácticas

- [ ] No hay N+1 queries (revisar relaciones JPA)
- [ ] Uso apropiado de `@Transactional`
- [ ] No hay lógica pesada en constructores
- [ ] Uso correcto de Optional
- [ ] No hay code smells obvios (métodos muy largos, clases god, etc.)
- [ ] Inyección de dependencias correcta (constructor injection)

### 9. Documentación

- [ ] Métodos complejos tienen JavaDoc
- [ ] Clases públicas tienen descripción clara
- [ ] Cambios en README si afectan configuración/despliegue
- [ ] Actualizaciones en documentación de arquitectura si aplica

### 10. Git y Convenciones

- [ ] Branch name descriptivo y siguiendo convención
- [ ] Commits atómicos y bien nombrados
- [ ] No hay merge conflicts
- [ ] El MR pasa CI/CD
- [ ] No hay warnings del compilador sin justificar

## Proceso de Revisión Sugerido

1. **Vista General** (5 min)
   - Leer descripción del MR y objetivo
   - Verificar que la estructura de archivos tenga sentido
   - Revisar commits para entender el flujo de cambios

2. **Revisión por Capas** (20-30 min)
   - Empezar por Domain (lo más crítico)
   - Continuar con Application
   - Revisar Infrastructure Input y Output
   - Verificar Tests

3. **Revisión de Integración** (10 min)
   - ¿Las capas se comunican correctamente?
   - ¿Los mappers funcionan bien?
   - ¿La inyección de dependencias es correcta?

4. **Feedback** (5 min)
   - Agrupar comentarios por severidad: bloqueantes, sugerencias, nitpicks
   - Señalar patrones positivos para reforzar buenas prácticas
   - Ser constructivo y específico

## Severidad de Comentarios

### 🔴 Bloqueante (Must Fix)
- Violación de arquitectura
- Problemas de seguridad
- Lógica de negocio incorrecta
- Bugs críticos
- Tests faltantes para funcionalidad crítica

### 🟡 Importante (Should Fix)
- Code smells
- Falta de logs importantes
- Problemas de performance evidentes
- Falta de validaciones
- Naming inconsistente

### 🟢 Sugerencia (Nice to Have)
- Mejoras de legibilidad
- Refactors menores
- Optimizaciones no críticas
- Nitpicks de estilo

## Plantilla de Comentario

```markdown
**[SEVERIDAD]** Descripción del problema

**Por qué es importante:**
[Explicación del impacto]

**Sugerencia:**
```java
// Código sugerido
```

**Referencia:**
Ver `copilot-instructions.md` para patrones y convenciones del proyecto.
```

## Preguntas Clave Durante la Revisión

- ¿Este código podría vivir en Domain sin saber que existe Spring?
- ¿El Use Case tiene lógica de negocio o solo orquesta?
- ¿El Controller tiene lógica de negocio o solo adapta?
- ¿Puedo testear esto fácilmente?
- ¿Los nombres de las clases siguen las convenciones?
- ¿Hay duplicación de código que pueda extraerse?
- ¿Está el código en la capa correcta?

## Referencias Rápidas

### Convenciones de Nombres

#### Application Layer
- Interface Use Case: `{Entidad}{Acción}` (e.g., `EmpleadoCreator`, `TramiteByIdFinder`)
- Implementación Use Case: `{Entidad}{Acción}UseCase`
- Command: `{Entidad}{Acción}Command`
- Result: `{Entidad}{Acción}Result`
- Mapper: `{Entidad}{Acción}UseCaseMapper`

#### Domain Layer
- Puerto de Salida: `{Entidad}{Acción}OutputPort`
- Entidades: Nombre simple (e.g., `Empleado`, `Tramite`, `Licencia`)

#### Infrastructure Input
- Adapter GET: `{Entidad}{Acción}GetAdapter`
- Adapter POST: `{Entidad}FormPostAdapter` o `{Entidad}PostAdapter`
- Mapper: `{Entidad}{Acción}AdapterMapper` o `{Entidad}PostAdapterMapper`
- ModelView: `{Entidad}ModelView`

#### Infrastructure Output
- Adapter directo: `{Entidad}{Acción}OutputAdapter`
- Adapter de Persistencia: `{Entidad}{Acción}Adapter`
- Mapper: `{Entidad}{Acción}OutputAdapterMapper` o `{Entidad}{Acción}AdapterMapper`
- Entidad JPA: `{Entidad}Entity`
- Repositorio: `{Entidad}Repository`

### Recursos
- [Clean Architecture - Uncle Bob](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)
- Documentación interna del proyecto: `copilot-instructions.md`
