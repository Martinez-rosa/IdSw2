# Abstracción de interfaz

## ¿Por qué?

El desarrollo de sistemas complejos enfrenta un desafío fundamental en la comunicación entre componentes. Cuando las interfaces entre módulos se diseñan de manera inconsistente, incompleta o excesivamente detallada, surgen numerosos problemas que afectan la calidad y mantenibilidad del software.

Estas deficiencias en la comunicación entre componentes se manifiestan en diversos problemas:

- **Interfaces confusas**: Comunicación entre componentes con contratos ambiguos o inconsistentes que llevan a malentendidos y errores difíciles de detectar.
- **Exceso de información expuesta**: Componentes que revelan detalles internos innecesarios, creando dependencias ocultas y dificultando futuros cambios.
- **Funcionalidad omitida**: Interfaces incompletas que obligan a los consumidores a buscar alternativas o implementar soluciones paralelas.
- **Duplicación funcional**: Comportamientos similares implementados de manera diferente por distintos componentes, generando inconsistencias y confusión.
- **Falta de evolución controlada**: Sin una abstracción adecuada, las interfaces cambian frecuentemente, afectando a todos los componentes dependientes.

Un síntoma claro de esta problemática es el "code smell" de "[Clases alternativas con interfaces diferentes](sc.acdi.md)", donde diferentes componentes proporcionan funcionalidades similares pero con interfaces incompatibles, complicando innecesariamente el sistema.

Cuando la abstracción de interfaz se realiza deficientemente, el código manifiesta comportamientos como:

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

Estos componentes realizan operaciones conceptualmente idénticas, pero con interfaces tan diferentes que complican su uso, comprensión y mantenimiento.

## ¿Qué?

La Abstracción de Interfaz es un principio de diseño que define cómo los componentes exponen su funcionalidad y se comunican entre sí, proporcionando contratos claros, consistentes y adecuadamente abstraídos.

> *Una interfaz bien diseñada no sólo describe lo que un sistema hace, sino que revela su intención, ocultando detalles no esenciales.*
>
> — Kent Beck

Este principio formaliza la separación entre el "qué" (la funcionalidad proporcionada) y el "cómo" (los detalles de implementación), estableciendo los fundamentos para un acoplamiento bajo y una evolución controlada.

|Principio del menor compromiso|Principio de la menor sorpresa|
|-|-|
|Formulado por Abelson y Sussman, este principio establece que la interfaz de un objeto debe proporcionar su comportamiento esencial, y nada más.|Este principio establece que una abstracción debe capturar todo el comportamiento de un objeto, ni más ni menos, sin ofrecer sorpresas o efectos secundarios.|
|*La interfaz de un módulo debería capturar la mínima cantidad de suposiciones que los clientes necesitan para usarlo correctamente.*|*Los sistemas deberían comportarse de manera que los usuarios no se sorprendan con comportamientos inesperados.*|
|Esto implica que una interfaz debe:|En términos prácticos:|
|- Incluir solo las operaciones necesarias|- Las funciones deben hacer lo que sus nombres sugieren|
|- Evitar detalles de implementación|- Los comportamientos similares deben seguir patrones consistentes|
|- Minimizar las restricciones sobre los clientes|- Las excepciones al comportamiento común deben estar claramente señaladas|

### Características de interfaces bien diseñadas

Una interfaz de calidad debe ser:

1. **Suficiente**: Debe capturar suficientes características de la abstracción para permitir una interacción significativa y eficiente. De lo contrario, el componente sería inútil.

   ```java
   // Interfaz insuficiente
   public interface Coleccion {
       void agregar(Object elemento);
       // Falta método para recuperar elementos
   }
   
   // Interfaz suficiente
   public interface Coleccion {
       void agregar(Object elemento);
       Object obtener(int indice);
   }
   ```

1. **Completa**: Debe cubrir todos los aspectos significativos de la abstracción, siendo lo suficientemente general como para ser comúnmente utilizable por cualquier cliente.

   ```java
   // Interfaz más completa para una colección
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

1. **Primitiva**: Las operaciones deben ser fundamentales e indiscutiblemente necesarias. Una operación es primitiva si solo puede implementarse a través del acceso a la representación subyacente o si, aunque podría implementarse sobre otras operaciones primitivas, hacerlo sería significativamente menos eficiente.

   ```java
   // Operaciones primitivas
   public interface Coleccion<T> {
       void agregar(T elemento);       // Primitiva: requiere acceso al estado interno
       T obtener(int indice);          // Primitiva: requiere acceso al estado interno
       int tamaño();                   // Primitiva: requiere conocer estado interno
       
       // No primitiva: podría implementarse usando las anteriores
       default boolean estaVacia() {
           return tamaño() == 0;
       }
   }
   ```

### Tipos de interfaces en el diseño de software

En función del contexto y nivel de abstracción, podemos identificar diferentes tipos de interfaces:

1. **Interfaces de programación (API)**: Definen cómo los componentes de software interactúan entre sí.

   ```java
   public interface RepositorioCliente {
       Cliente buscarPorId(String id);
       List<Cliente> buscarPorNombre(String nombre);
       void guardar(Cliente cliente);
       void eliminar(String id);
   }
   ```

1. **Interfaces de usuario (UI)**: Definen cómo los usuarios interactúan con el sistema.

1. **Interfaces de comunicación**: Definen cómo los sistemas interactúan entre sí (por ejemplo, REST, SOAP, gRPC).

1. **Interfaces de abstracción**: Crean una capa de abstracción sobre operaciones similares.

   ```java
   // Interfaz de abstracción para diferentes almacenamientos
   public interface Almacenamiento {
       void escribir(String clave, byte[] datos);
       byte[] leer(String clave);
       boolean existe(String clave);
       void eliminar(String clave);
   }
   
   // Implementaciones para diferentes tecnologías
   public class AlmacenamientoArchivo implements Almacenamiento { /*...*/ }
   public class AlmacenamientoS3 implements Almacenamiento { /*...*/ }
   public class AlmacenamientoRedis implements Almacenamiento { /*...*/ }
   ```

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

#### Clases alternativas con interfaces diferentes

Componentes que realizan tareas similares pero con interfaces incompatibles.
  
  ```java
  // Problema: Interfaces diferentes para operaciones similares
  interface GestorArchivos {
      byte[] obtenerContenido(String ruta);
      void guardarContenido(String ruta, byte[] datos);
  }
  
  interface ManejadorDocumentos {
      String leerDocumento(String id);
      boolean escribirDocumento(String id, String contenido);
  }
  
  // Solución: Interfaz unificada
  interface GestorContenido {
      byte[] leer(String identificador);
      void escribir(String identificador, byte[] contenido);
  }
  ```

#### Comportamiento obvio no implementado

Interfaces que omiten operaciones que los clientes razonablemente esperarían.

  ```java
  // Problema: Operación obvia faltante
  interface Coleccion<T> {
      void agregar(T elemento);
      T obtener(int indice);
      // Falta método para eliminar elementos
  }
  
  // Solución: Incluir operación esperada
  interface Coleccion<T> {
      void agregar(T elemento);
      T obtener(int indice);
      void eliminar(int indice);
  }
  ```

#### Responsabilidad fuera de lugar

Operaciones ubicadas en componentes inapropiados.

  ```java
  // Problema: Responsabilidad mal ubicada
  class Cliente {
      // ...
      public void guardarEnBaseDatos() {
          // Lógica de persistencia dentro de la entidad
      }
  }
  
  // Solución: Reubicar responsabilidad
  class Cliente {
      // Solo datos y comportamiento de dominio
  }
  
  interface RepositorioCliente {
      void guardar(Cliente cliente);
  }
  ```

### Diseñar interfaces sólidas

#### Nombrado semántico

Los nombres de interfaces y métodos deben comunicar claramente su propósito:

```java
// Poco claro
interface IProcessor {
    Object process(Object input);
}

// Claro y semánticamente rico
interface TransformadorTexto {
    String transformar(String entrada);
}
```

#### Consistencia en patrones

Mantener convenciones coherentes a través de todas las interfaces:

```java
// Inconsistente
interface RepositorioCliente {
    Cliente buscarPorId(String id);
    List<Cliente> getByName(String name);
    void save(Cliente cliente);
    void deleteCustomer(String id);
}

// Consistente
interface RepositorioCliente {
    Cliente buscarPorId(String id);
    List<Cliente> buscarPorNombre(String nombre);
    void guardar(Cliente cliente);
    void eliminar(String id);
}
```

#### Granularidad adecuada

Diseñar interfaces con el nivel adecuado de detalle:

```java
// Demasiado granular
interface Autenticador {
    boolean validarCredenciales(String usuario, String contraseña);
}

interface GestorSesiones {
    String crearSesion(String idUsuario);
}

interface ValidadorTokens {
    boolean validarToken(String token);
}

// Granularidad adecuada
interface ServicioAutenticacion {
    ResultadoAutenticacion autenticar(String usuario, String contraseña);
    boolean validarSesion(String token);
    void cerrarSesion(String token);
}
```

#### Segregar las interfaces

Dividir interfaces grandes en más pequeñas y específicas:

```java
// Interfaz monolítica
interface Documento {
    void abrir();
    void leer();
    void escribir();
    void formatear();
    void imprimir();
    void mostrarPrevisualizacion();
}

// Interfaces segregadas
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

#### Patrones de diseño para interfaces

Varios patrones facilitan el diseño de interfaces efectivas:

- **Adaptador**: Convertir una interfaz en otra que los clientes esperan.
  
  ```java
  // Interfaz existente
  interface LectorLegacy {
      String leerTodo();
  }
  
  // Interfaz requerida
  interface LectorStreaming {
      byte[] leerBloque(int tamaño);
  }
  
  // Adaptador
  class AdaptadorLector implements LectorStreaming {
      private LectorLegacy lectorOriginal;
      
      public AdaptadorLector(LectorLegacy lector) {
          this.lectorOriginal = lector;
      }
      
      @Override
      public byte[] leerBloque(int tamaño) {
          // Adaptar la interfaz antigua a la nueva
          String contenido = lectorOriginal.leerTodo();
          // Convertir y devolver solo el bloque solicitado
          return contenido.getBytes();
      }
  }
  ```

- **Fachada**: Proporcionar una interfaz unificada para un conjunto de interfaces.
  
  ```java
  // Subsistemas complejos
  class SistemaAutenticacion { /* ... */ }
  class SistemaAutorizacion { /* ... */ }
  class SistemaNotificacion { /* ... */ }
  
  // Fachada con interfaz simplificada
  class SeguridadFachada {
      private SistemaAutenticacion autenticacion;
      private SistemaAutorizacion autorizacion;
      private SistemaNotificacion notificacion;
      
      public boolean iniciarSesion(String usuario, String contraseña) {
          // Coordina los subsistemas
          boolean autenticado = autenticacion.verificarCredenciales(usuario, contraseña);
          if (autenticado) {
              autorizacion.cargarPermisos(usuario);
              notificacion.enviarAlerta(usuario, "Inicio de sesión exitoso");
          }
          return autenticado;
      }
  }
  ```

- **Abstract Factory**: Proporcionar una interfaz para crear familias de objetos relacionados.

### Evolucionar interfaces de manera controlada

#### Versionado de interfaces

Cuando se requieren cambios incompatibles, utilizar versionado explícito:

```java
// Versión original
package com.empresa.api.v1;

public interface ServicioCliente {
    Cliente buscarPorId(int id);
    void registrar(Cliente cliente);
}

// Nueva versión con cambios incompatibles
package com.empresa.api.v2;

public interface ServicioCliente {
    Optional<Cliente> buscarPorId(String id); // Cambió el tipo de ID y el retorno
    String registrar(DatosRegistroCliente datos); // Cambió el parámetro y retorno
}
```

#### Evolución compatible

Cuando sea posible, preferir cambios compatibles con versiones anteriores:

```java
public interface ServicioNotificacion {
    // Método original
    void enviarNotificacion(String destinatario, String mensaje);
    
    // Evolución compatible: nuevo método sobrecargado
    default void enviarNotificacion(String destinatario, String mensaje, Prioridad prioridad) {
        // Implementación por defecto que llama al método original
        enviarNotificacion(destinatario, mensaje);
    }
}
```

#### Deprecación gradual

Marcar métodos obsoletos pero mantenerlos temporalmente:

```java
public interface RepositorioUsuario {
    // Método original, ahora obsoleto
    @Deprecated
    Usuario buscarPorNombreUsuario(String nombreUsuario);
    
    // Método nuevo recomendado
    Optional<Usuario> encontrarPorNombreUsuario(String nombreUsuario);
}
```

### Documentar efectivamente las interfaces

La documentación es crucial para interfaces bien diseñadas:

```java
/**
 * Proporciona operaciones para autenticar usuarios en el sistema.
 * <p>
 * Este servicio gestiona el proceso de verificación de credenciales
 * y la creación/validación de sesiones de usuario.
 * <p>
 * Implementaciones de esta interfaz deben ser thread-safe.
 */
public interface ServicioAutenticacion {
    
    /**
     * Autentica a un usuario usando sus credenciales.
     *
     * @param usuario El identificador único del usuario (no puede ser null)
     * @param contraseña La contraseña del usuario (no puede ser null)
     * @return Resultado de la autenticación incluyendo token de sesión
     *         si la autenticación fue exitosa
     * @throws AutenticacionException Si ocurre un error durante la autenticación
     * @throws IllegalArgumentException Si usuario o contraseña son null
     */
    ResultadoAutenticacion autenticar(String usuario, String contraseña);
    
    /**
     * Verifica si un token de sesión es válido y no ha expirado.
     *
     * @param token El token de sesión a validar
     * @return true si el token es válido y activo, false en caso contrario
     */
    boolean validarSesion(String token);
    
    // Otros métodos...
}
```
