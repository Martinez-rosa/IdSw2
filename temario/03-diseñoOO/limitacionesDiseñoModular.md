# Limitaciones del diseño modular

Este ejemplo ilustra perfectamente el desafío de encontrar el equilibrio adecuado en la modularización:

## Enfoque 1: Una única clase asume toda la responsabilidad

```java
class Persona {
    Cabeza cabeza;
    Tronco tronco;
    Extremidades[] extremidades;
    Pene pene;
    Vagina vagina;
    String tipo;
    
    Persona(String tipo) {
        // ...
    }
    
    void bailar() {
        // ...
    }
    
    void correr() {
        // ...
    }
    
    void miccionar() {
        if (tipo.equals("Hombre")) {
            // ...
        } else {
            // ...
        }
    }
}

// Uso
Cliente client = new Persona("Mujer");
```

### Problemas identificados

- **Baja cohesión**: La clase tiene múltiples responsabilidades.
- **Clase grande**: Propenso a crecer con nuevos subtipos.
- **Validaciones por tipo**: Uso de condicionales en lugar del polimorfismo.

## Enfoque 2: Una clase por cada tipo de elemento

```java
class Hombre {
    Cabeza cabeza;
    Tronco tronco;
    Extremidades[] extremidades;
    Pene pene;
    
    Hombre() {
        // ...
    }
    
    void bailar() {
        // ...
    }
    
    void correr() {
        // ...
    }
    
    void miccionar() {
        // ...
    }
}

class Mujer {
    Cabeza cabeza;
    Tronco tronco;
    Extremidades[] extremidades;
    Vagina vagina;
    
    Mujer() {
        // ...
    }
    
    void bailar() {
        // ...
    }
    
    void correr() {
        // ...
    }
    
    void miccionar() {
        // ...
    }
}

// Uso
Cliente client = new Mujer(); // o new Hombre();
```

### Problemas identificados

- **Alto acoplamiento**: Clientes conocen *todas* las clases derivadas.
- **DRY**: Código duplicado en las clases (bailar, correr).
- **Dificultad para extensión**: Cada nuevo tipo requiere una clase *completa* nueva.

## Enfoque 3: Jerarquía de clases

Una mejor solución podría ser:

```java
abstract class Persona {
    Cabeza cabeza;
    Tronco tronco;
    Extremidades[] extremidades;
    
    // Métodos comunes
    void bailar() {
        // Implementación común
    }
    
    void correr() {
        // Implementación común
    }
    
    // Método abstracto para comportamientos específicos
    abstract void miccionar();
}

class Hombre extends Persona {
    Pene pene;
    
    @Override
    void miccionar() {
        // Implementación específica para hombre
    }
}

class Mujer extends Persona {
    Vagina vagina;
    
    @Override
    void miccionar() {
        // Implementación específica para mujer
    }
}

// Uso
Persona persona = madre.getPersona();
```

### Ventajas

- **Alta cohesión**: Cada clase tiene una responsabilidad clara
- **Bajo acoplamiento**: Los clientes trabajan con la abstracción (Persona)
- **DRY**: Código común centralizado en la clase base
- **Flexibilidad**: Soporta polimorfismo y extensibilidad

### Problemas identificados

En el diseño con herencia propuesto, un cliente podría intentar diferenciar el comportamiento según el tipo concreto:

```java
public class Recepcionista {
    public void recibir(Persona persona) {
        System.out.println("Soy un recepcionista que se alegra de su visita");
        if (persona instanceof Mujer) {
            System.out.println("Soy un recepcionista que se admira de su belleza");
            ((Mujer) persona).escucharHalago();
            System.out.println("Soy un recepcionista que se admira de su existencia");
            ((Mujer) persona).escucharPiropo();
        } else {
            ((Hombre) persona).recibirPalmada(this);
        }
    }
}
```

Este enfoque presenta varios problemas:

1. Al diferenciar el comportamiento según el tipo concreto, estamos indicando que las subclases no son verdaderamente intercambiables.
1. Cuando se añada un nuevo tipo (por ejemplo, `Androide`), se han de modificar todos los bloques `if-else` que comprueban tipos.
1. Los clientes deben conocer todos los tipos concretos en la jerarquía, lo cual crea un alto acoplamiento.
1. No podemos añadir nuevas implementaciones sin modificar el código existente.

## Enfoque 4: técnica del doble despacho

La técnica del Doble Despacho resuelve este problema mediante la delegación de la responsabilidad de "saber qué tipo soy" al propio objeto, aprovechando el polimorfismo en lugar de las comprobaciones de tipo.

```java
// Clase base con método aceptar para el visitante
abstract class Persona {
    // Atributos y métodos comunes
    Cabeza cabeza;
    Tronco tronco;
    Extremidades[] extremidades;
    
    void bailar() { /* ... */ }
    void correr() { /* ... */ }
    
    // Método para el doble despacho
    abstract void aceptar(VisitantePersona visitante);
}

class Hombre extends Persona {
    Pene pene;
    
    void miccionar() { /* ... */ }
    
    @Override
    void aceptar(VisitantePersona visitante) {
        visitante.visitar(this); // Primer despacho
    }
}

class Mujer extends Persona {
    Vagina vagina;
    
    void miccionar() { /* ... */ }
    
    @Override
    void aceptar(VisitantePersona visitante) {
        visitante.visitar(this); // Primer despacho
    }
}

// Interfaz para los visitantes
interface VisitantePersona {
    void visitar(Hombre hombre);
    void visitar(Mujer mujer);
    // Nuevos tipos requieren nuevos métodos aquí
}

// Cliente implementa el visitante
class ProcesadorPersona implements VisitantePersona {
    @Override
    public void visitar(Hombre hombre) {
        // Comportamiento específico para hombre
        hombre.miccionar();
        // Otras operaciones específicas...
    }
    
    @Override
    public void visitar(Mujer mujer) {
        // Comportamiento específico para mujer
        mujer.miccionar();
        // Otras operaciones específicas...
    }
    
    public void procesar(Persona persona) {
        // Comportamiento común
        persona.bailar();
        
        // Doble despacho para comportamiento específico
        persona.aceptar(this); // Segundo despacho
    }
}
```

### Ventajas

1. No se necesitan comprobaciones de tipo (`instanceof`) en tiempo de ejecución.
1. Añadir un nuevo tipo solo requiere:
   - Crear la nueva subclase de `Persona`
   - Añadir un nuevo método `visitar` en la interfaz `VisitantePersona`
   - Implementar este método en las clases visitantes existentes
1. No hay discriminación basada en el tipo concreto dentro del código cliente.
1. Cada comportamiento específico está encapsulado en su propio método.

### Problemas identificados

1. **Complejidad adicional**: Introduce más interfaces y clases.
1. **Dispersión de comportamiento**: El comportamiento relacionado con una entidad puede estar disperso en varios visitantes.
1. **Acoplamiento circular**: Hay acoplamiento entre las jerarquías de visitante y visitado.
1. **Evolución**: Añadir un nuevo tipo de Persona requiere modificar la interfaz Visitante y todas sus implementaciones.

## Reflexión "*final*"

Debemos de buscar el equilibrio y asumir compromisos en diseño orientado a objetos:

1. **Diseño monolítico** (una única clase): Simple pero rígido y poco cohesivo.
1. **Diseño fragmentado** (una clase por tipo sin herencia): Evita condicionales pero viola DRY.
1. **Diseño con herencia simple**: Mejor pero puede llevar a uso de `instanceof`.
1. **Diseño con doble despacho**: Elegante pero más complejo.

La elección entre estas alternativas debería guiarse por:

- La complejidad del dominio
- La probabilidad de extensión futura
- El equilibrio entre simplicidad inmediata y mantenibilidad a largo plazo

No hay una respuesta universalmente correcta - el mejor diseño depende del contexto específico del problema y de cómo anticipamos que evolucionará el sistema.
