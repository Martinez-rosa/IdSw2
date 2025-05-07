# Aspiradora - Versión con Patrón Visitor Completo (v002extensible)

Esta implementación utiliza el patrón Visitor completo para lograr una separación total entre los tipos de celdas y las entidades que interactúan con ellas. Este enfoque sigue los principios SOLID, especialmente el Principio Abierto/Cerrado.

## Estructura General

- **Mundo**: Matriz bidimensional de celdas
- **Celda**: Clase abstracta con método genérico `aceptar(VisitanteCelda)`
- **VisitanteCelda**: Interfaz que define métodos de visita para cada tipo de celda
- **Entidad**: Interfaz común para aspiradora y gato
- **Aspiradora y Gato**: Implementan `Entidad` y `VisitanteCelda`
- **Simulacion**: Clase principal para ejecutar la simulación

## Patrón Visitor Completo

El patrón Visitor permite separar algoritmos de la estructura de objetos sobre la que operan, y añadir nuevos comportamientos sin modificar las clases existentes.

### 1. Interfaz Visitante

```java
public interface VisitanteCelda {
    boolean visitarCeldaLimpia(CeldaLimpia celda);
    boolean visitarCeldaSucia(CeldaSucia celda);
    boolean visitarCeldaObstaculo(CeldaObstaculo celda);
}
```

### 2. Método Aceptar en Celdas

```java
// En la clase abstracta Celda
public abstract boolean aceptar(VisitanteCelda visitante);

// En CeldaLimpia
@Override
public boolean aceptar(VisitanteCelda visitante) {
    return visitante.visitarCeldaLimpia(this);
}
```

### 3. Implementaciones Concretas

```java
// Aspiradora implementa VisitanteCelda
public class Aspiradora implements Entidad, VisitanteCelda {
    @Override
    public boolean visitarCeldaLimpia(CeldaLimpia celda) {
        // No hay nada que hacer en una celda limpia
        return false; // La celda no cambia
    }
    
    @Override
    public boolean visitarCeldaSucia(CeldaSucia celda) {
        // Limpiar la celda
        int nivelAnterior = celda.getNivelSuciedad();
        boolean limpiada = celda.reducirSuciedad();
        basuraRecogida++;
        // ...
        return limpiada;
    }
    
    // ...
}
```

## Mejoras respecto a las versiones anteriores

1. **Desacoplamiento total**:
   - Las celdas no conocen los tipos concretos de visitantes.
   - Los visitantes implementan una interfaz común.

2. **Facilidad de extensión**:
   - Se pueden añadir nuevos visitantes sin modificar la jerarquía de celdas.
   - Se pueden añadir nuevos tipos de celdas ampliando la interfaz VisitanteCelda.

3. **Cohesión y responsabilidad única**:
   - Cada visitante es responsable de sus propias operaciones.
   - Cada celda solo se preocupa de delegarse al método adecuado del visitante.

4. **Jerarquía clara**:
   - La interfaz `Entidad` proporciona un punto común para todas las entidades del sistema.
   - Se pueden agregar fácilmente nuevas entidades que implementen `VisitanteCelda`.

5. **Mayor escalabilidad**:
   - El sistema puede crecer en complejidad sin afectar a los componentes existentes.
   - Se pueden añadir nuevos comportamientos (visitantes) sin modificar la estructura.

## Conclusión

Esta implementación muestra la máxima flexibilidad del patrón Visitor, permitiendo:

1. Separar algoritmos de datos
2. Minimizar cambios en código existente
3. Facilitar la adición de nuevos comportamientos
4. Mantener un diseño limpio y orientado a objetos

La evolución desde la versión con `instanceof` hasta el patrón Visitor completo ilustra cómo mejorar progresivamente un diseño para hacerlo más mantenible y extensible.