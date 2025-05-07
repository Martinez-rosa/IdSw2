# Aspiradora - Versión con Doble Despacho Básico (v001basico)

Esta implementación utiliza el patrón de doble despacho básico para eliminar el uso de `instanceof` y hacer que las interacciones entre entidades y celdas sean más orientadas a objetos.

## Estructura General

- **Mundo**: Matriz bidimensional de celdas
- **Celda**: Clase abstracta con métodos de aceptación para visitantes
- **Aspiradora**: Entidad con métodos específicos para visitar cada tipo de celda
- **Gato**: Entidad con métodos específicos para visitar cada tipo de celda
- **Simulacion**: Clase principal para ejecutar la simulación

## Patrón de Doble Despacho

El doble despacho es un patrón donde la interacción entre dos objetos se delega a través de métodos específicos que permiten el polimorfismo en ambos lados de la interacción.

### 1. Primer Despacho en la Celda

```java
// En la clase abstracta Celda
public abstract boolean aceptarAspiradora(Aspiradora aspiradora);
public abstract boolean aceptarGato(Gato gato);

// En CeldaSucia
@Override
public boolean aceptarAspiradora(Aspiradora aspiradora) {
    return aspiradora.visitarCeldaSucia(this);
}

@Override
public boolean aceptarGato(Gato gato) {
    return gato.visitarCeldaSucia(this);
}
```

### 2. Segundo Despacho en la Entidad

```java
// En Aspiradora
public boolean visitarCeldaSucia(CeldaSucia celda) {
    int nivelAnterior = celda.getNivelSuciedad();
    boolean limpiada = celda.reducirSuciedad();
    basuraRecogida++;
    
    System.out.printf("Limpiado celda [%d,%d]. Nivel anterior: %d, Nivel actual: %s%n", 
                       celda.getFila(), celda.getColumna(), nivelAnterior,
                       limpiada ? "limpio" : celda.getNivelSuciedad());
    
    return limpiada;
}
```

## Mejoras respecto a la versión anterior

1. **Eliminación de `instanceof`**:
   - Las comprobaciones de tipo se eliminan completamente.
   - La lógica específica se encapsula en métodos de visita.

2. **Encapsulación de comportamiento**:
   - Cada clase conoce su propio comportamiento ante diferentes interacciones.
   - La lógica específica se localiza en el visitante correcto.

3. **Mayor extensibilidad**:
   - Para añadir un nuevo tipo de celda, solo hay que implementar los métodos `aceptar`.
   - Los visitantes existentes tendrán que implementar nuevos métodos de visita.

## Limitaciones que aún presenta

1. **Acoplamiento a las clases concretas**:
   - Tanto `Aspiradora` como `Gato` están acoplados a todos los tipos concretos de celdas.
   - Aún se requiere modificar estas clases al añadir nuevos tipos.

2. **Falta de interfaz común**:
   - No existe una interfaz unificada para los visitantes.
   - La expansión del sistema con nuevos visitantes requiere cambios en la jerarquía de `Celda`.

La siguiente versión abordará estas limitaciones implementando el patrón Visitor completo.