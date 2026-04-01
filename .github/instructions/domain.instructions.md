---
description: "Use when creating or modifying Domain entities, value objects, output port contracts, and business validation rules in this repository. Includes unit testing requirements and requires a short implementation plan before coding."
applyTo: "src/main/java/**/domain/**/*.java"
---

# domain.instructions.md

## Objetivo
Definir reglas de implementacion para la capa domain de la API REST Seguridad Barrial siguiendo Clean Architecture, Domain-Driven Design (DDD) y preservando la pureza del modelo de negocio.

## Alcance
- Entidades de dominio (Aggregate Roots y Entities).
- Value Objects inmutables.
- OutputPorts.
- Excepciones y validaciones puras de negocio.

## Excepciones de Negocio
- Las excepciones de negocio se definen en domain, sin dependencias de framework.
- Representan situaciones esperadas del negocio: entidad no encontrada, regla de negocio violada, conflicto de datos (ej: `PropietarioNotFoundException`, `DniDuplicadoException`).
- Extienden `RuntimeException`.
- No contienen lógica, solo el mensaje que describe la violación.

## Planificacion Previa (obligatoria)
- Antes de codificar, definir un plan breve de 3-5 pasos.
- Confirmar que la regla o concepto pertenece realmente al negocio y no a infraestructura.
- Confirmar que contratos de salida necesita application hacia infrastructure/output.
- Implementar luego de validar el plan y actualizarlo al terminar cada paso.

## Reglas Obligatorias
- No usar dependencias a Spring, JPA, web ni clases de infraestructura.
- No agregar anotaciones de framework.
- Modelar conceptos de negocio y sus invariantes, no detalles tecnicos.
- Mantener OutputPorts pequenos y con contratos claros.
- No ubicar DTOs, Commands, Results ni ModelViews en domain.

## Tacticas DDD
- Distinguir Entities (tienen identidad) de Value Objects (se definen por sus atributos).
- Encapsular invariantes de negocio dentro de las entidades de dominio.
- Usar Aggregate Roots como punto de entrada para modificar un cluster de objetos relacionados.
- Usar `UUID` como tipo de identificador por defecto en entidades de dominio. Solo usar otro tipo (ej: `Long`) con justificacion explicita.

## Patron Value Object
- Los Value Objects son **inmutables**: todos los campos `final`, sin setters.
- Validar invariantes en el constructor; lanzar `IllegalArgumentException` si los datos son invalidos.
- Implementar `equals()` y `hashCode()` basados en todos los atributos (o usar record de Java).
- Nombrar Value Objects con el concepto de negocio que representan (ej: `Dni`, `Email`, `Direccion`, `Telefono`).
- No usar Lombok `@Setter` ni `@Data` en Value Objects; preferir `@Value` de Lombok o Java records.
- **Todas** las propiedades de una entidad de dominio deben ser Value Objects; no usar tipos primitivos ni `String` directamente. Esto incluye nombre, apellido, telefono, email, direccion, etc. Solo `UUID id` queda exento por ser identidad de la entidad.

## Checklist Rapido
- [ ] La clase no depende de frameworks.
- [ ] El nombre representa un concepto de negocio real.
- [ ] No hay imports de infraestructura.
- [ ] El puerto de salida define un contrato claro y acotado.
- [ ] La validacion pertenece realmente al dominio.
- [ ] No se usa domain como transporte de datos entre capas.
- [ ] Los Value Objects son inmutables con validacion en constructor.
- [ ] Todas las propiedades de entidades son Value Objects (excepto `UUID id`).
- [ ] Las entidades encapsulan sus invariantes de negocio.

## Ejemplo Minimo

### Value Object
```java
public class Dni {

  private final String valor;

  public Dni(String valor) {
    if (valor == null || !valor.matches("\\d{7,8}")) {
      throw new IllegalArgumentException("El DNI debe tener 7 u 8 digitos");
    }
    this.valor = valor;
  }

  public String getValor() {
    return valor;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Dni dni = (Dni) o;
    return valor.equals(dni.valor);
  }

  @Override
  public int hashCode() {
    return valor.hashCode();
  }
}
```

### Entidad de Dominio con Value Objects
```java
public class Propietario {

  private final UUID id;
  private final Nombre nombre;
  private final Apellido apellido;
  private final Dni dni;
  private final Direccion direccion;
  private final Telefono telefono;
  private final Email email;

  public Propietario(UUID id, Nombre nombre, Apellido apellido, Dni dni,
                     Direccion direccion, Telefono telefono, Email email) {
    if (id == null) throw new IllegalArgumentException("El id es obligatorio");
    if (nombre == null) throw new IllegalArgumentException("El nombre es obligatorio");
    if (apellido == null) throw new IllegalArgumentException("El apellido es obligatorio");
    if (dni == null) throw new IllegalArgumentException("El dni es obligatorio");
    if (direccion == null) throw new IllegalArgumentException("La direccion es obligatoria");
    if (telefono == null) throw new IllegalArgumentException("El telefono es obligatorio");
    if (email == null) throw new IllegalArgumentException("El email es obligatorio");
    // Cada Value Object ya validó sus propias invariantes en su constructor.
    this.id = id;
    this.nombre = nombre;
    this.apellido = apellido;
    this.dni = dni;
    this.direccion = direccion;
    this.telefono = telefono;
    this.email = email;
  }

  // getters
}
```

### OutputPort
```java
public interface PropietarioCreatorOutputPort {
  Propietario perform(Propietario propietario);
}
```

## Testing Integrado (obligatorio durante desarrollo)
- Estrategia por defecto: cubrir casos de dominio desde tests de application (Use Cases) usando mappers reales.
- Crear tests unitarios directos de domain solo cuando exista logica de negocio no trivial aislada.
- Si hay tests directos de domain, deben ser puros, sin contexto Spring.
- Para Value Objects con validaciones relevantes, crear tests directos que validen:
  - Construccion correcta con datos validos.
  - Rechazo con `IllegalArgumentException` para datos invalidos.
  - Igualdad por valor (`equals`/`hashCode`).
- Para OutputPorts, validar desde application el contrato esperado; no agregar logica tecnica en domain.

## Definition Of Done
- Domain expresa negocio y no infraestructura.
- Las invariantes relevantes estan encapsuladas.
- No hay dependencias indebidas a frameworks.
- Los casos de dominio necesarios quedan cubiertos en tests de application, y solo se agregan tests directos de domain para logica no trivial aislada.
- Los contratos de salida estan listos para implementarse en infrastructure/output.