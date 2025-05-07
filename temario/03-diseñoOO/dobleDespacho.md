# Doble despacho

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