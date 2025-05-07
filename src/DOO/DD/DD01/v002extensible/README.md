# Zoológico - Versión 2: Patrón Visitor Completo

<div align=center>

![](/images/temario/03-diseñoOO/src/01-DD/v002-extensible/v002.svg)

</div>

## Descripción

En esta tercera versión, implementamos el patrón Visitor completo:

1. Creamos una interfaz `VisitanteAnimal` con métodos para visitar diferentes tipos de animales.
2. `Animal` tiene un método `aceptar(VisitanteAnimal)`.
3. Cada subclase de `Animal` implementa `aceptar` llamando al método apropiado del visitante.
4. Múltiples clases pueden implementar `VisitanteAnimal` (como `Cuidador` y `Veterinario`).

```java
// Interfaz Visitor
public interface VisitanteAnimal {
    void visitar(Perro perro);
    void visitar(Pajaro pajaro);
}

// Clase abstracta Animal con métodos polimórficos
public abstract void moverse();
public abstract void comunicarse();
public abstract void aceptar(VisitanteAnimal visitante);

// Implementación en Perro
@Override
public void moverse() {
    System.out.println("El perro corre en cuatro patas");
}

@Override
public void aceptar(VisitanteAnimal visitante) {
    visitante.visitar(this);
}

// En Cuidador (un visitante)
public void cuidar(Animal animal) {
    animal.aceptar(this);
}
```

## Mejoras respecto a la versión anterior

1. **Generalización del doble despacho**:
   - Múltiples entidades (`Cuidador`, `Veterinario`, etc.) pueden ser "visitantes".
   - No estamos limitados a un único tipo de interacción.

2. **Mayor reutilización**:
   - El mecanismo de visita está generalizado a través de la interfaz.
   - Diferentes visitantes pueden compartir la misma estructura sin duplicar código.

3. **Desacoplamiento**:
   - `Animal` ya no está acoplado a `Cuidador` sino a la interfaz `VisitanteAnimal`.
   - Podemos añadir nuevos visitantes sin modificar la jerarquía de `Animal`.

4. **Mayor escalabilidad**:
   - Se pueden añadir más tipos de visitantes (como `Entrenador`, `Fotógrafo`, etc.) sin cambiar `Animal`.
   - Cada visitante implementa su comportamiento específico.

## Consideraciones

1. **Extensibilidad**:
   - Para añadir un nuevo tipo de animal, hay que modificar la interfaz `VisitanteAnimal` y todas sus implementaciones.
   - Para añadir un nuevo visitante, solo se crea una nueva clase que implemente `VisitanteAnimal`.

2. **Complejidad**:
   - La estructura es más compleja que las versiones anteriores.
   - El flujo de control puede ser más difícil de seguir debido al doble despacho.

3. **Ventajas**:
   - Elimina completamente los condicionales basados en tipos.
   - Permite añadir nuevos comportamientos sin modificar las clases existentes.
   - Separa claramente las operaciones de los datos.