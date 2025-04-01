# Tópicos avanzados de interfaces

## Patrones de diseño para interfaces

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

## Evolucionar interfaces de manera controlada

### Versionado de interfaces

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

### Evolución compatible

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

### Deprecación gradual

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

## Documentar efectivamente las interfaces

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
