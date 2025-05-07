# El Zoológico

El sistema simula la interacción de diferentes tipos de animales con sus cuidadores y veterinarios. 

Cuando un animal está en el zoológico:

1. Un **cuidador** lo alimenta según su tipo.
2. Un **veterinario** lo examina de manera específica.
3. La interacción varía según el tipo de animal.

Durante este flujo, se personalizan ciertos comportamientos según si el animal es un perro o un pájaro:

* Los perros mueven la cola y ladran.
* Los pájaros cantan y se posan en la mano.

## [Primera versión](v000mal)

Uso de `instanceof` y casting.

### Descripción

En la [primera versión](v000mal), utilizamos una única clase `Animal` con un atributo `tipo`:

* El `Cuidador` detecta el tipo de animal usando condicionales `if-else`.
* Aplica comportamientos específicos según el tipo.

```java
if (animal.getTipo().equals("Pajaro")) {
    System.out.println("Le doy semillas especiales para aves");
    // ...
} else if (animal.getTipo().equals("Perro")) {
    System.out.println("Le doy croquetas premium");
    // ...
}
```

### Problemas

1. Violación del Principio Abierto/Cerrado (OCP)
   * Cada vez que agregamos un nuevo tipo de animal, debemos modificar el `Cuidador`.
   * Se acumula lógica condicional basada en tipos concretos.
2. Acoplamiento fuerte
   * El `Cuidador` conoce todos los detalles de los tipos de animales.
3. Difícil de extender o mantener
   * Si añadimos, por ejemplo, `Gato` o `Reptil`, el código crecerá en complejidad.

## [Segunda versión](v001basico)

Doble despacho con métodos `aceptar()`.

### Descripción

Aquí se introduce el **doble despacho manual**:

* `Animal` declara un método abstracto `aceptar(Cuidador cuidador)`.
* Cada subclase (`Perro`, `Pajaro`) implementa este método invocando `cuidador.visitar(this)`.

Así, el `Cuidador` ya no usa `instanceof`, sino que define métodos sobrecargados:

```java
public void alimentar(Animal animal) {
    animal.aceptar(this); // primer despacho
}

public void visitar(Perro perro) { ... } // segundo despacho
public void visitar(Pajaro pajaro) { ... }
```

<div align=center>

![](/images/temario/03-diseñoOO/src/01-DD/DD.svg)

</div>

### Cómo mejora sobre la primera

1. Elimina `instanceof` y `casting`.
2. Inversión del control:
   * Ya no es el `Cuidador` quien decide el tipo, sino que es el `Animal` quien delega el comportamiento correcto.
3. Mayor encapsulamiento y adherencia a OCP.
   * Para añadir un nuevo tipo de animal, solo se añade un nuevo método `visitar(...)`.

### Aún presenta limitaciones

* El diseño sigue **acoplado a la clase `Cuidador`**. Si queremos que otras entidades (como un `Veterinario`) hagan algo similar, tendríamos que replicar toda la lógica.
* **No hay una interfaz común** para el comportamiento "visitador".

---

## [Tercera versión](v002extensible)

Interfaz `VisitanteAnimal`.

### Descripción

En esta versión se formaliza el patrón **Visitor**:

* Se define una interfaz `VisitanteAnimal` con métodos `visitar(Perro)` y `visitar(Pajaro)`.
* Tanto `Cuidador` como `Veterinario` implementan esta interfaz.
* `Animal.aceptar(...)` recibe cualquier `VisitanteAnimal`.

Esto permite que múltiples actores puedan interactuar con los animales sin tener que saber exactamente qué tipo son.

```java
public abstract class Animal {
    public abstract void aceptar(VisitanteAnimal visitante);
}

public class Veterinario implements VisitanteAnimal {
    public void visitar(Perro perro) { ... }
    public void visitar(Pajaro pajaro) { ... }
}
```

### Cómo mejora sobre la segunda

1. Generaliza el doble despacho: ahora múltiples entidades (`Cuidador`, `Veterinario`, etc.) pueden ser "visitadores".
2. Reutilización del patrón: el mecanismo ya no depende de una sola clase. Se puede extender fácilmente.
3. Desacoplamiento y cohesión: `Animal` ya no está ligado a `Cuidador`. Solo sabe de la interfaz `VisitanteAnimal`.
4. Mayor escalabilidad: Se pueden añadir más visitantes (por ejemplo: `Entrenador`, `Fotógrafo`, etc.) sin tocar `Animal`.

## Comparativa

|Versión|Técnica usada|Problemas principales|Mejoras en la siguiente|
|-|-|-|-|
|Primera|`instanceof` + condiciones|Acoplamiento, fragilidad, violación OCP|Segunda elimina condicionales y acopla menos|
|Segunda|Doble despacho manual|Acoplamiento a una clase específica|Tercera generaliza mediante interfaz|
|Tercera|Patrón Visitor con interfaz|Ninguno grave. Diseño limpio y extensible|—|