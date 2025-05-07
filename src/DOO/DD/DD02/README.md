# Aspiradora - Tres Enfoques de Implementación

Este directorio contiene tres implementaciones diferentes del problema de la aspiradora, cada una mostrando un nivel progresivo de diseño orientado a objetos, enfocándose especialmente en el patrón de doble despacho y el patrón Visitor.

## El Problema

Simular una aspiradora autónoma que debe limpiar una superficie rectangular con zonas de diferente nivel de suciedad. Incluye:

- Una superficie representada como matriz 2D
- Una aspiradora con movimiento aleatorio o dirigido
- Un gato que ocasionalmente aparece para ensuciar
- Diferentes niveles de suciedad (1-4)
- Batería y bolsa de basura limitadas

## Las Tres Implementaciones

### 1. [v000mal](v000mal/README.md) - Uso de instanceof

Esta primera versión utiliza `instanceof` para identificar tipos y realizar acciones específicas:

```java
public void limpiar(Mundo mundo) {
    Celda celdaActual = mundo.getCelda(posicion.getFila(), posicion.getColumna());
    
    if (celdaActual instanceof CeldaSucia) {
        CeldaSucia celdaSucia = (CeldaSucia) celdaActual;
        // Lógica específica para celdas sucias...
    }
}
```

**Principales problemas**: Acoplamiento fuerte, violación del Principio Abierto/Cerrado, fragilidad ante cambios.

### 2. [v001basico](v001basico/README.md) - Doble Despacho Básico

La segunda versión implementa el doble despacho para eliminar el uso de `instanceof`:

```java
// En CeldaSucia
@Override
public boolean aceptarAspiradora(Aspiradora aspiradora) {
    return aspiradora.visitarCeldaSucia(this);
}

// En Aspiradora
public boolean visitarCeldaSucia(CeldaSucia celda) {
    // Lógica específica para celdas sucias...
    return limpiada;
}
```

**Mejoras**: Eliminación de comprobaciones de tipo, encapsulamiento de comportamientos específicos.  
**Limitaciones**: Sigue habiendo acoplamiento a clases concretas.

### 3. [v002extensible](v002extensible/README.md) - Patrón Visitor Completo

La versión final implementa el patrón Visitor completo con interfaces y desacoplamiento total:

```java
// Interfaz Visitor
public interface VisitanteCelda {
    boolean visitarCeldaLimpia(CeldaLimpia celda);
    boolean visitarCeldaSucia(CeldaSucia celda);
    boolean visitarCeldaObstaculo(CeldaObstaculo celda);
}

// En CeldaSucia
@Override
public boolean aceptar(VisitanteCelda visitante) {
    return visitante.visitarCeldaSucia(this);
}

// Aspiradora implementa VisitanteCelda
public class Aspiradora implements Entidad, VisitanteCelda {
    @Override
    public boolean visitarCeldaSucia(CeldaSucia celda) {
        // Lógica específica para celdas sucias...
    }
}
```

**Mejoras**: Desacoplamiento total, extensibilidad máxima, cohesión y responsabilidad única.

## Comparativa

| Aspecto | v000mal | v001basico | v002extensible |
|---------|---------|------------|----------------|
| Uso de `instanceof` | Sí | No | No |
| Extensibilidad | Baja | Media | Alta |
| Acoplamiento | Alto | Medio | Bajo |
| Cohesión | Baja | Media | Alta |
| Complejidad del código | Baja | Media | Media-Alta |
| Mantenibilidad | Baja | Media | Alta |
| Adición de visitantes | Difícil | Difícil | Fácil |
| Adición de celdas | Difícil | Media | Media |

## Conclusión

Este ejercicio demuestra cómo evolucionar un diseño desde un enfoque directo pero rígido (con `instanceof`) hacia un diseño flexible y extensible (patrón Visitor). Cada versión representa un paso en esta evolución, mostrando las ventajas y compromsios de cada enfoque.

La evolución de estos tres enfoques sigue el mismo patrón que vimos en los ejemplos de Doble Despacho con el restaurante (DD00) y el zoológico (DD01), reforzando la comprensión del patrón y su aplicación a diferentes dominios.