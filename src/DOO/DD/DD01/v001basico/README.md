# Zoológico - Versión 1: Doble Despacho Básico

<div align=center>

![](/images/temario/03-diseñoOO/src/01-DD/v001-basico/v001.svg)

</div>

## Descripción

En esta segunda versión, implementamos el doble despacho de manera directa:

1. `Animal` se convierte en una clase abstracta con un método `aceptar(Cuidador)`.
2. Las subclases `Perro` y `Pajaro` implementan este método invocando el método apropiado del cuidador.
3. `Cuidador` define métodos sobrecargados para cada tipo de animal.

```java
// Clase abstracta Animal
public abstract void moverse();
public abstract void comunicarse();
public abstract void aceptar(Cuidador cuidador);

// Implementación en Perro
@Override
public void moverse() {
    System.out.println("El perro corre en cuatro patas");
}

@Override
public void aceptar(Cuidador cuidador) {
    cuidador.visitar(this);
}

// En Cuidador
public void alimentar(Animal animal) {
    animal.aceptar(this);
}

public void visitar(Perro perro) {
    // Comportamiento específico para perro
}

public void visitar(Pajaro pajaro) {
    // Comportamiento específico para pájaro
}
```

## Mejoras respecto a la versión anterior

1. **Eliminación de `instanceof` y condiciones**:
   - Ya no necesitamos comprobar el tipo de animal.
   - La selección del comportamiento se realiza mediante polimorfismo.
   - Los comportamientos comunes se definen como métodos abstractos en la clase base `Animal`.

2. **Inversión del control**:
   - Ya no es el `Cuidador` quien decide el tipo, sino que es el `Animal` quien delega el comportamiento correcto.

3. **Mayor encapsulamiento y adherencia a OCP**:
   - Para añadir un nuevo tipo de animal, solo se añade una nueva subclase y un nuevo método `visitar`.
   - No hay que modificar el código existente en la clase `Animal` o en `Cuidador.alimentar()`.

## Limitaciones

1. **Acoplamiento al Cuidador**:
   - La clase `Animal` está acoplada directamente a la clase `Cuidador`.
   - Si queremos añadir otro tipo de visitante (por ejemplo, un `Veterinario`), necesitaríamos modificar la jerarquía de `Animal`.

2. **No hay una interfaz común para los visitantes**:
   - No hay abstracción para el comportamiento "visitador".
   - Esto hace difícil la extensión para múltiples tipos de visitantes.

En la siguiente versión, resolveremos estas limitaciones con el patrón Visitor completo.