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

> Sigue en [doble despacho](dobleDespacho.md)
