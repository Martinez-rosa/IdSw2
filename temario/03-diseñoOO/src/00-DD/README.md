# Un restaurante

El sistema simula la interacción de un **restaurante** con diferentes tipos de **personas** (hombres o mujeres). 

Cuando una persona entra al restaurante:

1. Es saludada por un **recepcionista**.
2. Luego la persona pide algo.
3. El **camarero** le sirve.
4. Finalmente, la persona se despide y el camarero recoge la mesa.

Durante este flujo, se personalizan ciertos comportamientos según si la persona es hombre o mujer.

* A los hombres se les da una palmada y ellos dejan propina.
* A las mujeres se les lanza un piropo o se les invita un cóctel.

## [Primera versión](v000)

Uso de `instanceof` y casting

### Descripción

En la [primera versión](v000), el objeto `Recepcionista` tiene un método `recibir(Persona persona)` que:

* Detecta si la persona es un `Hombre` o una `Mujer` usando `instanceof`.
* Realiza un *cast* explícito para invocar comportamientos específicos de esa subclase.

```java
if (persona instanceof Mujer) {
    ((Mujer) persona).escucharHalago();
    ((Mujer) persona).escucharPiropo();
} else {
    ((Hombre) persona).recibirPalmada(this);
}
```

### Problemas

1. Violación del Principio Abierto/Cerrado (OCP)
   * Cada vez que agregamos un nuevo tipo de persona, debemos modificar el `Recepcionista`.
   * Se acumula lógica condicional basada en tipos concretos.
1. Acoplamiento fuerte
   * El `Recepcionista` conoce todas las subclases de `Persona`. Está fuertemente acoplado a `Hombre` y `Mujer`.
1. Difícil de extender o mantener
   * Si añadimos, por ejemplo, `Niño` o `Androide`, el `Recepcionista` crecerá en complejidad.

## [Segunda versión](v001-basico)

Doble despacho con métodos `aceptar()`

### Descripción

Aquí se introduce el **doble despacho manual**:

* `Persona` declara un método abstracto `aceptar(Recepcionista recepcionista)`.
* Cada subclase (`Hombre`, `Mujer`) implementa este método invocando `recepcionista.visitar(this)`.

Así, el `Recepcionista` ya no usa `instanceof`, sino que define métodos sobrecargados:

```java
public void recibir(Persona persona) {
    persona.aceptar(this); // primer despacho
}

public void visitar(Hombre hombre) { ... } // segundo despacho
public void visitar(Mujer mujer)  { ... }
```

### Cómo mejora sobre la primera

1. Elimina `instanceof` y `casting`.
2. Inversión del control:
   * Ya no es el `Recepcionista` quien decide el tipo, sino que es la `Persona` quien delega el comportamiento correcto.
3. Mayor encapsulamiento y adherencia a OCP.
   * Para añadir un nuevo tipo de persona, solo se añade un nuevo método `visitar(...)`.

### Aún presenta limitaciones

* El diseño sigue **acoplado a la clase `Recepcionista`**. Si queremos que otras entidades (como un `Camarero`) hagan algo similar, tendríamos que replicar toda la lógica o extenderla.
* **No hay una interfaz común** para el comportamiento “visitador”, lo que limita la reutilización y la abstracción.

---

## [Tercera versión](v002-extensible)

Interfaz `VisitadorPersona`

### Descripción

En esta versión se formaliza el patrón **Visitor**:

* Se define una interfaz `VisitadorPersona` con métodos `visitar(Hombre)` y `visitar(Mujer)`.
* Tanto `Recepcionista` como `Camarero` implementan esta interfaz.
* `Persona.aceptar(...)` recibe cualquier `VisitadorPersona`.

Esto permite que múltiples actores puedan interactuar con las personas sin tener que saber exactamente qué tipo son.

```java
public abstract class Persona {
    public abstract void aceptar(VisitadorPersona visitador);
}

public class Camarero implements VisitadorPersona {
    public void visitar(Hombre hombre) { ... }
    public void visitar(Mujer mujer)  { ... }
}
```

### Cómo mejora sobre la segunda

1. Generaliza el doble despacho: ahora múltiples entidades (`Recepcionista`, `Camarero`, etc.) pueden ser "visitadores".
1. Reutilización del patrón: el mecanismo ya no depende de una sola clase. Se puede extender fácilmente.
1. Desacoplamiento y cohesión: `Persona` ya no está ligada a `Recepcionista`. Solo sabe de la interfaz `VisitadorPersona`.
1. Mayor escalabilidad: Se pueden añadir más visitadores (por ejemplo: `Chef`, `Gerente`, etc.) sin tocar `Persona`.

## Comparativa

|Versión|Técnica usada|Problemas principales|Mejoras en la siguiente|
|-|-|-|-|
|Primera|`instanceof` + casting|Acoplamiento, fragilidad, violación OCP|Segunda elimina condicionales y acopla menos|
|Segunda|Doble despacho manual|Acoplamiento a una clase específica|Tercera generaliza mediante interfaz|
|Tercera|Patrón Visitor con interfaz|Ninguno grave. Diseño limpio y extensible|—|
