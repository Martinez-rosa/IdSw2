# Abstracción de interfaz

## ¿Por qué?

Entiendase por interfaz como el punto de conexión entre diferentes partes de un sistema de software, en el que se definen un conjunto de operaciones, métodos o funciones que un componente expone para ser utilizados por otros componentes, estableciendo claramente:

<div align=center>

|Que operaciones|Que parámetros|Que resultados|Que errores o excepciones|
|-|-|-|-|
|están disponibles|requieren|devuelven|pueden producirse|

</div>

> *Una interfaz bien diseñada no sólo describe lo que un sistema hace, sino que revela su intención, ocultando detalles no esenciales.*
>
> — Kent Beck

El desarrollo de sistemas complejos enfrenta un desafío fundamental en la comunicación entre componentes. Cuando las interfaces entre módulos se diseñan de manera inconsistente, incompleta o excesivamente detallada, surgen numerosos problemas que afectan la calidad y mantenibilidad del software.

Estas deficiencias en la comunicación entre componentes se manifiestan en diversos problemas:

- **Interfaces confusas**: Comunicación entre componentes con contratos ambiguos o inconsistentes que llevan a malentendidos y errores difíciles de detectar.
- **Exceso de información expuesta**: Componentes que revelan detalles internos innecesarios, creando dependencias ocultas y dificultando futuros cambios.
- **Funcionalidad omitida**: Interfaces incompletas que obligan a los consumidores a buscar alternativas o implementar soluciones paralelas.
- **Duplicación funcional**: Comportamientos similares implementados de manera diferente por distintos componentes, generando inconsistencias y confusión.
- **Falta de evolución controlada**: Sin una abstracción adecuada, las interfaces cambian frecuentemente, afectando a todos los componentes dependientes.

Un síntoma claro de esta problemática es el "code smell" de "[Clases alternativas con interfaces diferentes](sc.acdi.md)", donde diferentes componentes proporcionan funcionalidades similares pero con interfaces incompatibles, complicando innecesariamente el sistema.

Cuando la abstracción de interfaz se realiza deficientemente, el código manifiesta comportamientos como:

<div align=center>
<table>
<tr>
<th>Componente 1: Gestión de usuarios</th><th>Componente 2: Sistema similar para clientes</th>
</tr>
<tr>
<td>

```java
public class GestorUsuarios {
    public boolean agregarUsuario(String nombre, int edad, String email) { /*...*/ }
    public Usuario buscarUsuarioPorNombre(String nombre) { /*...*/ }
    public void eliminarUsuario(int id) { /*...*/ }
}
```
</td>
<td>

```java
public class SistemaClientes {
    public int darDeAltaCliente(Cliente datos) { /*...*/ }
    public Cliente obtenerCliente(String nombreCompleto) { /*...*/ }
    public boolean borrarClientePorIdentificador(int idCliente) { /*...*/ }
}
```
</td>
</tr>
</table>
</div>

Estos componentes realizan operaciones conceptualmente idénticas, pero con interfaces tan diferentes que complican su uso, comprensión y mantenimiento.

## ¿Qué?

La Abstracción de interfaz es un principio de diseño que define cómo los componentes exponen su funcionalidad y se comunican entre sí, proporcionando contratos claros, consistentes y adecuadamente abstraídos.

Este principio formaliza la separación entre el "qué" (la funcionalidad proporcionada) y el "cómo" (los detalles de implementación), estableciendo los fundamentos para un acoplamiento bajo y una evolución controlada.

<div align=center>

|Principio del menor compromiso|Principio de la menor sorpresa|
|-|-|
|Formulado por Abelson y Sussman, este principio establece que la interfaz de un objeto debe proporcionar su comportamiento esencial, y nada más.|Este principio establece que una abstracción debe capturar todo el comportamiento de un objeto, ni más ni menos, sin ofrecer sorpresas o efectos secundarios.|
|*La interfaz de un módulo debería capturar la mínima cantidad de suposiciones que los clientes necesitan para usarlo correctamente.*|*Los sistemas deberían comportarse de manera que los usuarios no se sorprendan con comportamientos inesperados.*|
|Esto implica que una interfaz debe:|En términos prácticos:|
|- Incluir solo las operaciones necesarias|- Las funciones deben hacer lo que sus nombres sugieren|
|- Evitar detalles de implementación|- Los comportamientos similares deben seguir patrones consistentes|
|- Minimizar las restricciones sobre los clientes|- Las excepciones al comportamiento común deben estar claramente señaladas|

</div>

### Características de interfaces bien diseñadas

#### Suficiente

Debe capturar suficientes características de la abstracción para permitir una interacción significativa y eficiente. De lo contrario, el componente sería inútil.

<div align=center>
<table>
<tr>
<th>Insuficiente</th><th>Suficiente</th>
</tr>
<tr>
<td>

```java
public interface Coleccion {
    void agregar(Object elemento);
    // Falta método para recuperar elementos
}
```
</td><td>

```java
public interface Coleccion {
    void agregar(Object elemento);
    Object obtener(int indice);
}
```
</td>
</tr>
</table>
</div>

#### Completa

Debe cubrir todos los aspectos significativos de la abstracción, siendo lo suficientemente general como para ser comúnmente utilizable por cualquier cliente.

<div align=center>
<table>
<tr>
<th>Completa</th>
</tr>
<tr>
<td>

```java
public interface Coleccion<T> {
    void agregar(T elemento);
    T obtener(int indice);
    boolean contiene(T elemento);
    int tamaño();
    boolean estaVacia();
    void eliminar(T elemento);
    Iterator<T> iterador();
}
```
</td>
</tr>
</table>
</div>

#### Primitiva

Las operaciones deben ser fundamentales e indiscutiblemente necesarias. Una operación es primitiva si solo puede implementarse a través del acceso a la representación subyacente o si, aunque podría implementarse sobre otras operaciones primitivas, hacerlo sería significativamente menos eficiente.

<div align=center>
<table>
<tr>
<th>Primitiva</th>
</tr>
<tr>
<td>

```java
public interface Coleccion<T> {
    void agregar(T elemento);
    T obtener(int indice);
    int tamaño();
    
    // No primitiva: podría implementarse usando las anteriores
    default boolean estaVacia() {
        return tamaño() == 0;
    }
}
```
</td>
</tr>
</table>
</div>

> [Tipos de interfaces](interfacesTipos.md)

## ¿Para qué?

La aplicación consistente del principio de Abstracción de Interfaz produce sistemas con las siguientes características positivas:

- **Claridad contractual**: Las interacciones entre componentes se basan en contratos bien definidos que todos los participantes comprenden.
- **Ocultamiento de información**: Los detalles de implementación permanecen encapsulados, permitiendo cambios internos sin afectar a los consumidores.
- **Consistencia semántica**: Operaciones similares se expresan de manera similar en todo el sistema, facilitando el aprendizaje y reduciendo errores.
- **Evolución independiente**: Los componentes pueden evolucionar internamente sin afectar a sus consumidores mientras mantengan la interfaz.
- **Sustitución transparente**: Las implementaciones pueden ser reemplazadas siempre que respeten el contrato de la interfaz.

<div align=center>

| Abstracción de Interfaz Efectiva |||| Abstracción de Interfaz Deficiente |
|-|-:|:-:|:-|-|
|Contratos claros y explícitos      |**Fluidez**|     *vs*|**Viscosidad**  | Interfaces confusas o inconsistentes |
|Cambios de implementación ocultos  |**Flexibilidad**|*vs*|**Rigidez**| Cambios internos afectan a consumidores |
|Pruebas automáticas efectivas       |**Robustez**|*vs*|**Fragilidad**| Imposibilidad de verificar comportamiento |
|Fácil adición de implementaciones  |**Reusabilidad**|    *vs*|**Inmovilidad**  | Difícil adaptación a nuevos contextos |

</div>

La metáfora del "enchufe eléctrico" ilustra perfectamente este principio:

> "Una buena interfaz funciona como un enchufe eléctrico estándar: ofrece un contrato claro (voltaje, forma), oculta detalles complejos (cómo se genera la electricidad), permite sustitución (diferentes fuentes de energía) y posibilita evolución independiente (la red eléctrica puede actualizarse sin cambiar los enchufes)."

## ¿Cómo?

Para aplicar efectivamente el principio de Abstracción de Interfaz en el diseño de software, se pueden seguir estas estrategias prácticas:

### Identificar y resolver problemas de interfaz

El primer paso es detectar síntomas de interfaces mal diseñadas:

- [Clases alternativas con interfaces diferentes](sc.acdi.md)
- [Comportamiento obvio no implementado](sc.coni.md)
- [Responsabilidad fuera de lugar](sc.mr.md)

### Diseñar interfaces sólidas

#### Nombrado semántico

Los nombres de interfaces y métodos deben comunicar claramente su propósito.

<div align=center>
<table>
<tr>
<th>Poco claro</th><th>Mejor</th>
</tr>
<tr>
<td>
    
```java
interface IProcessor {
    Object process(Object input);
}
```
</td><td>

```java
interface TransformadorTexto {
    String transformar(String entrada);
}
```   
</td>
</tr>
</table>
</div>

#### Consistencia en patrones

Mantener convenciones coherentes a través de todas las interfaces.


<div align=center>
<table>
<tr>
<th>Inconsistente</th><th>Consistente</th>
</tr>
<tr>
<td>

```java
interface RepositorioCliente {
    Cliente buscarPorId(String id);
    List<Cliente> getByName(String name);
    void save(Cliente cliente);
    void deleteCustomer(String id);
}
```
</td><td>

```java
interface RepositorioCliente {
    Cliente buscarPorId(String id);
    List<Cliente> buscarPorNombre(String nombre);
    void guardar(Cliente cliente);
    void eliminar(String id);
}
```
</td>
</tr>
</table>
</div>

#### Granularidad adecuada

Diseñar las interfaces con el nivel adecuado de detalle.

<div align=center>
<table>
<tr>
<th>Demasiado</th><th>Mejor</th>
</tr>
<tr>
<td>

```java
interface Autenticador {
    boolean validarCredenciales(String usuario,
                                String contraseña);
}

interface GestorSesiones {
    String crearSesion(String idUsuario);
}

interface ValidadorTokens {
    boolean validarToken(String token);
}
```
</td><td>


```java
interface ServicioAutenticacion {
    ResultadoAutenticacion autenticar(String usuario,
                                      String contraseña);
    boolean validarSesion(String token);
    void cerrarSesion(String token);
}
```
</td>
</tr>
</table>
</div>

#### Segregar las interfaces

Dividir interfaces grandes en más pequeñas y específicas.

<div align=center>
<table>
<tr>
<th>Monolito</th><th>Mejor</th>
</tr>
<tr>
<td>

```java
interface Documento {
    void abrir();
    void leer();
    void escribir();
    void formatear();
    void imprimir();
    void mostrarPrevisualizacion();
}
```
</td>
<td>

```java
interface DocumentoLegible {
    void abrir();
    void leer();
}

interface DocumentoEditable extends DocumentoLegible {
    void escribir();
    void formatear();
}

interface DocumentoImprimible {
    void imprimir();
    void mostrarPrevisualizacion();
}
```
</td>
</tr>
</table>
</div>

### Técnicas de implementación

#### Interfaces explícitas vs. implícitas

En lenguajes con soporte para interfaces explícitas (Java, C#), utilizarlas para declarar contratos formales:

```java
// Interfaz explícita en Java
public interface Ordenable {
    int comparar(Ordenable otro);
}

public class Producto implements Ordenable {
    private String nombre;
    private double precio;
    
    @Override
    public int comparar(Ordenable otro) {
        Producto otroProducto = (Producto) otro;
        return Double.compare(this.precio, otroProducto.precio);
    }
}
```

En lenguajes con tipado pato (Python, JavaScript), documentar claramente las interfaces implícitas:

```python
# Interfaz implícita en Python
class Ordenable:
    """
    Interfaz Ordenable: Todo objeto que implemente esta interfaz debe
    proporcionar un método comparar(otro) que devuelva:
    - valor negativo si este objeto es menor que otro
    - cero si son iguales
    - valor positivo si este objeto es mayor que otro
    """
    def comparar(self, otro):
        raise NotImplementedError("Método 'comparar' debe ser implementado")

class Producto:
    def __init__(self, nombre, precio):
        self.nombre = nombre
        self.precio = precio
    
    def comparar(self, otro):
        """Implementación de interfaz Ordenable"""
        return self.precio - otro.precio
```

#### Interfaces fluidas

> [Interfaces fluidas](ejemplo/src/interfacesFluidas/README.md)

Diseñar interfaces que permitan encadenamiento de métodos para operaciones relacionadas:

```java
// Interfaz fluida
public class ConsultaSQL {
    public ConsultaSQL seleccionar(String... campos) {
        // Implementación
        return this;
    }
    
    public ConsultaSQL desde(String tabla) {
        // Implementación
        return this;
    }
    
    public ConsultaSQL donde(String condicion) {
        // Implementación
        return this;
    }
    
    public ConsultaSQL ordenarPor(String campo) {
        // Implementación
        return this;
    }
    
    public ResultadoConsulta ejecutar() {
        // Implementación
        return new ResultadoConsulta();
    }
}

// Uso
ResultadoConsulta resultado = new ConsultaSQL()
    .seleccionar("id", "nombre", "precio")
    .desde("productos")
    .donde("precio > 100")
    .ordenarPor("nombre")
    .ejecutar();
```

> [+Advanced](interfacesAvanzado.md)
