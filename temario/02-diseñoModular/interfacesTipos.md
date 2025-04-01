# Tipos de interfaces en el diseño de software

En función del contexto y nivel de abstracción, podemos identificar diferentes tipos de interfaces:

## Interfaces de programación (API)

Definen cómo los componentes de software interactúan entre sí.

```java
public interface RepositorioCliente {
    Cliente buscarPorId(String id);
    List<Cliente> buscarPorNombre(String nombre);
    void guardar(Cliente cliente);
    void eliminar(String id);
}
```

## Interfaces de usuario (UI)

Definen cómo los usuarios interactúan con el sistema.

## Interfaces de comunicación

Definen cómo los sistemas interactúan entre sí (por ejemplo, REST, SOAP, gRPC).

## Interfaces de abstracción

Crean una capa de abstracción sobre operaciones similares.

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
