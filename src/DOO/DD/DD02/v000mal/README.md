# Aspiradora - Versión con instanceof (v000mal)

Esta versión implementa el problema de la aspiradora usando `instanceof` para identificar el tipo de celda y realizar acciones específicas. Este enfoque se considera "malo" en diseño orientado a objetos por varias razones.

## Estructura General

- **Mundo**: Matriz bidimensional de celdas
- **Celda**: Clase abstracta con subclases CeldaLimpia, CeldaSucia y CeldaObstaculo
- **Aspiradora**: Entidad capaz de moverse y limpiar
- **Gato**: Entidad capaz de moverse y ensuciar
- **Simulacion**: Clase principal para ejecutar la simulación

## Interacciones Clave (con instanceof)

### En Aspiradora.limpiar()

```java
public void limpiar(Mundo mundo) {
    Celda celdaActual = mundo.getCelda(posicion.getFila(), posicion.getColumna());
    
    // Uso de instanceof para identificar el tipo de celda
    if (celdaActual instanceof CeldaSucia) {
        CeldaSucia celdaSucia = (CeldaSucia) celdaActual;
        // Lógica de limpieza...
    }
}
```

### En Gato.ensuciar()

```java
public void ensuciar(Mundo mundo) {
    Celda celdaActual = mundo.getCelda(posicion.getFila(), posicion.getColumna());
    
    // Uso de instanceof para identificar el tipo de celda
    if (celdaActual instanceof CeldaLimpia) {
        // Convertir a celda sucia...
    } else if (celdaActual instanceof CeldaSucia) {
        // Aumentar suciedad...
    }
}
```

## Problemas de Diseño

1. **Acoplamiento Fuerte**: Las clases Aspiradora y Gato están acopladas a los tipos concretos de celdas.
   
2. **Violación del Principio Abierto/Cerrado**: Si se añaden nuevos tipos de celdas, hay que modificar el código de Aspiradora y Gato.

3. **Fragilidad**: Los cambios en la jerarquía de Celda pueden romper el código en Aspiradora y Gato.

4. **Duplicación de Lógica**: La lógica de identificación de tipos se repite en diferentes lugares.

5. **Problema de Extensibilidad**: Añadir nuevos comportamientos requiere modificar múltiples clases.

Esta implementación funciona, pero no sigue las mejores prácticas de diseño orientado a objetos. Las siguientes versiones abordarán estos problemas utilizando el patrón de doble despacho.