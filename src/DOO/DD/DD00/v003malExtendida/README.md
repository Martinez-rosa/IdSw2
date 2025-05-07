# Restaurante - Versión "mal" extendida

<div align=center>

![](/images/temario/03-diseñoOO/src/DD00/v003malExtendida/v003.svg)

</div>

## Descripción

Esta versión extiende la implementación inicial con `instanceof` para incluir un nuevo tipo de visitante: los `Androides`.

En esta versión, tanto el `Recepcionista` como el `Camarero` identifican el tipo de persona usando `instanceof` y realizan casting explícito:

```java
// En Recepcionista.java
public void recibir(Persona persona) {
    System.out.println("Soy un recepcionista que se alegra de su visita");
    
    if (persona instanceof Mujer) {
        // Comportamiento específico para Mujer
        ((Mujer) persona).escucharHalago();
    } else if (persona instanceof Androide) {
        // Comportamiento específico para Androide
        ((Androide) persona).mostrarTecnologia();
    } else {
        // Por defecto, asumimos que es un Hombre
        ((Hombre) persona).recibirPalmada(this);
    }
}
```

## Problemas evidentes

1. **Violación del Principio Abierto/Cerrado (OCP)**:
   - Cada vez que agregamos un nuevo tipo de persona (como el Androide), debemos modificar el código de todas las clases que interactúan con personas.
   - Se acumula lógica condicional basada en tipos concretos.

2. **Acoplamiento fuerte**:
   - Tanto `Recepcionista` como `Camarero` conocen todos los tipos concretos de `Persona`.
   - Están fuertemente acoplados a `Hombre`, `Mujer` y ahora a `Androide`.

3. **Fragilidad**:
   - Si añadimos más tipos de personas, la estructura if-else-if se vuelve más compleja y propensa a errores.
   - El orden de las comprobaciones es importante (si se cambia el orden, puede cambiar el comportamiento).

4. **Difícil de extender**:
   - Añadir un nuevo tipo de `Persona` (por ejemplo, `Niño`) requeriría modificar múltiples clases.
   - Cada nuevo tipo aumenta la complejidad ciclomática de los métodos que usan `instanceof`.

5. **Comportamientos específicos dispersos**:
   - Las interacciones específicas entre visitantes y personas están distribuidas en múltiples clases.

## Comparación con la versión que usa el patrón Visitor

La versión que usa el patrón Visitor (v003extendida) resuelve estos problemas al:

1. Delegar la responsabilidad de "saber qué tipo soy" al propio objeto, evitando `instanceof`.
2. Permitir añadir nuevos tipos (como `Androide`) sin modificar el código existente, solo extendiendo las interfaces.
3. Centralizar los comportamientos específicos en clases coherentes.
4. Facilitar la extensión del sistema con nuevos tipos o nuevos visitantes.