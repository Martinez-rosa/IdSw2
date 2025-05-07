# Limitaciones del diseño modular

Este ejemplo ilustra perfectamente el desafío de encontrar el equilibrio adecuado en la modularización:

## Enfoque 1: Una única clase asume toda la responsabilidad

```java
class Animal {
    Cabeza cabeza;
    Cuerpo cuerpo;
    Extremidades[] extremidades;
    Alas alas;
    Cola cola;
    String tipo; // "Perro", "Pájaro", etc.
    
    Animal(String tipo) {
        // ...
    }
    
    void moverse() {
        if (tipo.equals("Perro")) {
            // Correr en cuatro patas
        } else if (tipo.equals("Pájaro")) {
            // Volar batiendo alas
        }
    }
    
    void comunicarse() {
        if (tipo.equals("Perro")) {
            // Ladrar
        } else if (tipo.equals("Pájaro")) {
            // Cantar
        }
    }
    
    void comer() {
        if (tipo.equals("Perro")) {
            // Masticar carnívoro
        } else if (tipo.equals("Pájaro")) {
            // Picotear semillas
        }
    }
}

// Uso
Cliente client = new Animal("Pájaro");
```

### Problemas identificados

- **Baja cohesión**: La clase tiene múltiples responsabilidades.
- **Clase grande**: Propenso a crecer con nuevos subtipos.
- **Validaciones por tipo**: Uso de condicionales en lugar del polimorfismo.

## Enfoque 2: Una clase por cada tipo de elemento

```java
class Perro {
    Cabeza cabeza;
    Cuerpo cuerpo;
    Extremidades[] patas;
    Cola cola;
    
    Perro() {
        // ...
    }
    
    void moverse() {
        // Correr en cuatro patas
    }
    
    void comunicarse() {
        // Ladrar con distintos tonos
    }
    
    void comer() {
        // Masticar y devorar
    }
}

class Pajaro {
    Cabeza cabeza;
    Cuerpo cuerpo;
    Alas alas;
    Cola cola;
    
    Pajaro() {
        // ...
    }
    
    void moverse() {
        // Volar batiendo alas
    }
    
    void comunicarse() {
        // Cantar melodiosamente
    }
    
    void comer() {
        // Picotear con precisión
    }
}

// Uso
Cliente client = new Pajaro(); // o new Perro();
```

### Problemas identificados

- **Alto acoplamiento**: Clientes conocen *todas* las clases derivadas.
- **DRY**: Código duplicado en las clases (moverse, comunicarse).
- **Dificultad para extensión**: Cada nuevo tipo requiere una clase *completa* nueva.

## Enfoque 3: Jerarquía de clases

Una mejor solución podría ser:

```java
abstract class Animal {
    Cabeza cabeza;
    Cuerpo cuerpo;
    
    // Métodos comunes
    void respirar() {
        // Implementación común
    }
    
    void dormir() {
        // Implementación común
    }
    
    // Métodos abstractos para comportamientos específicos
    abstract void moverse();
    abstract void comunicarse();
    abstract void comer();
}

class Perro extends Animal {
    Extremidades[] patas;
    Cola cola;
    
    @Override
    void moverse() {
        // Correr en cuatro patas
    }
    
    @Override
    void comunicarse() {
        // Ladrar con distintos tonos
    }
    
    @Override
    void comer() {
        // Masticar y devorar
    }
}

class Pajaro extends Animal {
    Alas alas;
    Cola cola;
    
    @Override
    void moverse() {
        // Volar batiendo alas
    }
    
    @Override
    void comunicarse() {
        // Cantar melodiosamente
    }
    
    @Override
    void comer() {
        // Picotear con precisión
    }
}

// Uso
Animal animal = zoologico.getAnimal();
```

### Ventajas

- **Alta cohesión**: Cada clase tiene una responsabilidad clara
- **Bajo acoplamiento**: Los clientes trabajan con la abstracción (Animal)
- **DRY**: Código común centralizado en la clase base
- **Flexibilidad**: Soporta polimorfismo y extensibilidad

### Problemas identificados

En el diseño con herencia propuesto, un cliente podría intentar diferenciar el comportamiento según el tipo concreto:

```java
public class Cuidador {
    public void alimentar(Animal animal) {
        System.out.println("Soy un cuidador que alimenta a los animales");
        if (animal instanceof Pajaro) {
            System.out.println("Le doy semillas especiales para aves");
            ((Pajaro) animal).posarseEnMano();
            System.out.println("Le silbo una melodía que le gusta");
            ((Pajaro) animal).cantarFeliz();
        } else {
            System.out.println("Le doy croquetas premium");
            ((Perro) animal).moverCola();
        }
    }
}
```

Este enfoque presenta varios problemas:

1. Al diferenciar el comportamiento según el tipo concreto, estamos indicando que las subclases no son verdaderamente intercambiables.
1. Cuando se añada un nuevo tipo (por ejemplo, `Gato`), se han de modificar todos los bloques `if-else` que comprueban tipos.
1. Los clientes deben conocer todos los tipos concretos en la jerarquía, lo cual crea un alto acoplamiento.
1. No podemos añadir nuevas implementaciones sin modificar el código existente.

## Enfoque 4: técnica del doble despacho

La técnica del Doble Despacho resuelve este problema mediante la delegación de la responsabilidad de "saber qué tipo soy" al propio objeto, aprovechando el polimorfismo en lugar de las comprobaciones de tipo.

```java
// Clase base con método aceptar para el visitante
abstract class Animal {
    // Atributos y métodos comunes
    Cabeza cabeza;
    Cuerpo cuerpo;
    
    void respirar() { /* ... */ }
    void dormir() { /* ... */ }
    
    // Método para el doble despacho
    abstract void aceptar(VisitanteAnimal visitante);
}

class Perro extends Animal {
    Extremidades[] patas;
    Cola cola;
    
    void ladrar() { /* ... */ }
    void moverCola() { /* ... */ }
    
    @Override
    void aceptar(VisitanteAnimal visitante) {
        visitante.visitar(this); // Primer despacho
    }
}

class Pajaro extends Animal {
    Alas alas;
    Cola cola;
    
    void volar() { /* ... */ }
    void cantar() { /* ... */ }
    
    @Override
    void aceptar(VisitanteAnimal visitante) {
        visitante.visitar(this); // Primer despacho
    }
}

// Interfaz para los visitantes
interface VisitanteAnimal {
    void visitar(Perro perro);
    void visitar(Pajaro pajaro);
    // Nuevos tipos requieren nuevos métodos aquí
}

// Cliente implementa el visitante
class CuidadorAnimal implements VisitanteAnimal {
    @Override
    public void visitar(Perro perro) {
        // Comportamiento específico para perro
        System.out.println("Doy croquetas al perro");
        perro.moverCola();
        // Otras operaciones específicas...
    }
    
    @Override
    public void visitar(Pajaro pajaro) {
        // Comportamiento específico para pájaro
        System.out.println("Doy alpiste al pájaro");
        pajaro.cantar();
        // Otras operaciones específicas...
    }
    
    public void cuidar(Animal animal) {
        // Comportamiento común
        System.out.println("Limpio la jaula/caseta");
        
        // Doble despacho para comportamiento específico
        animal.aceptar(this); // Segundo despacho
    }
}
```

### Ventajas

1. No se necesitan comprobaciones de tipo (`instanceof`) en tiempo de ejecución.
1. Añadir un nuevo tipo solo requiere:
   - Crear la nueva subclase de `Animal`
   - Añadir un nuevo método `visitar` en la interfaz `VisitanteAnimal`
   - Implementar este método en las clases visitantes existentes
1. No hay discriminación basada en el tipo concreto dentro del código cliente.
1. Cada comportamiento específico está encapsulado en su propio método.

### Problemas identificados

1. **Complejidad adicional**: Introduce más interfaces y clases.
1. **Dispersión de comportamiento**: El comportamiento relacionado con una entidad puede estar disperso en varios visitantes.
1. **Acoplamiento circular**: Hay acoplamiento entre las jerarquías de visitante y visitado.
1. **Evolución**: Añadir un nuevo tipo de Animal requiere modificar la interfaz Visitante y todas sus implementaciones.

## Reflexión "*final*"

Debemos buscar el equilibrio y asumir compromisos en diseño orientado a objetos:

1. **Diseño monolítico** (una única clase): Simple pero rígido y poco cohesivo.
1. **Diseño fragmentado** (una clase por tipo sin herencia): Evita condicionales pero viola DRY.
1. **Diseño con herencia simple**: Mejor pero puede llevar a uso de `instanceof`.
1. **Diseño con doble despacho**: Elegante pero más complejo.

La elección entre estas alternativas debería guiarse por:

- La complejidad del dominio
- La probabilidad de extensión futura
- El equilibrio entre simplicidad inmediata y mantenibilidad a largo plazo

No hay una respuesta universalmente correcta - el mejor diseño depende del contexto específico del problema y de cómo anticipamos que evolucionará el sistema.
