# Broken Package Principles

Ocurre cuando la organización de clases en paquetes viola uno o más de los principios de diseño de paquetes establecidos por Robert C. Martin. Esta violación se manifiesta a través de dependencias mal estructuradas, paquetes sin cohesión clara y una arquitectura que dificulta el mantenimiento y la evolución del sistema.

## Ejemplo

### Problema

```java
package com.empresa.dominio;

public class Cliente {
    private String nombre;
    private String email;
    
    // Getters y setters
}

public class Producto {
    private String codigo;
    private double precio;
    
    // Getters y setters
}

// Paquete com.empresa.servicios
package com.empresa.servicios;

import com.empresa.dominio.Cliente;
import com.empresa.datos.ClienteRepository;

public class ClienteServicio {
    private ClienteRepository repository;
    
    public ClienteServicio() {
        this.repository = new ClienteRepository();
    }
    
    public void registrar(Cliente cliente) {
        repository.guardar(cliente);
    }
}

// Paquete com.empresa.datos
package com.empresa.datos;

import com.empresa.dominio.Cliente;
import com.empresa.servicios.ClienteServicio;

public class ClienteRepository {
    private ClienteServicio servicio; // ¡Dependencia circular!
    
    public ClienteRepository() {
        // Inicialización
    }
    
    public void guardar(Cliente cliente) {
        // Lógica para persistir un cliente
        System.out.println("Cliente guardado: " + cliente.getNombre());
    }
    
    public void notificarCambio() {
        servicio = new ClienteServicio(); // Crea una dependencia circular
    }
}
```

En este ejemplo, se pueden observar varios problemas que violan los principios de paquetes:

1. **Dependencia circular** entre los paquetes `com.empresa.servicios` y `com.empresa.datos`.
2. **Paquete de dominio sin cohesión** que agrupa entidades no relacionadas en su comportamiento.
3. **Instanciación directa** de dependencias, creando acoplamiento fuerte.
4. **Mezcla de responsabilidades** entre capas de servicio y persistencia.

### Solución propuesta

```java
// Paquete com.empresa.dominio.cliente
package com.empresa.dominio.cliente;

public class Cliente {
    private String nombre;
    private String email;
    
    // Getters y setters
}

// Paquete com.empresa.dominio.producto
package com.empresa.dominio.producto;

public class Producto {
    private String codigo;
    private double precio;
    
    // Getters y setters
}

// Paquete com.empresa.dominio.repositorio
package com.empresa.dominio.repositorio;

import com.empresa.dominio.cliente.Cliente;

public interface ClienteRepository {
    void guardar(Cliente cliente);
    Cliente buscarPorEmail(String email);
}

// Paquete com.empresa.servicio
package com.empresa.servicio;

import com.empresa.dominio.cliente.Cliente;
import com.empresa.dominio.repositorio.ClienteRepository;

public class ClienteServicio {
    private final ClienteRepository repository;
    
    public ClienteServicio(ClienteRepository repository) {
        this.repository = repository;
    }
    
    public void registrar(Cliente cliente) {
        repository.guardar(cliente);
    }
}

// Paquete com.empresa.infraestructura.persistencia
package com.empresa.infraestructura.persistencia;

import com.empresa.dominio.cliente.Cliente;
import com.empresa.dominio.repositorio.ClienteRepository;

public class ClienteRepositoryImpl implements ClienteRepository {
    
    public ClienteRepositoryImpl() {
        // Inicialización
    }
    
    @Override
    public void guardar(Cliente cliente) {
        // Lógica para persistir un cliente
        System.out.println("Cliente guardado: " + cliente.getNombre());
    }
    
    @Override
    public Cliente buscarPorEmail(String email) {
        // Implementación
        return new Cliente();
    }
}
```

La solución reorganiza los paquetes y las clases para resolver los problemas identificados:

1. **Eliminación de dependencias circulares**: Se introduce una interfaz `ClienteRepository` en el paquete de dominio, aplicando el Principio de Inversión de Dependencias.
1. **Mejor cohesión de paquetes**: Se separan las entidades en paquetes más específicos según su relación funcional.
1. **Dependencias en una sola dirección**: El flujo de dependencias va desde los servicios hacia el dominio y desde la infraestructura hacia el dominio.
1. **Dependencias**: Las dependencias se proveen mediante los constructores en lugar de instanciarse directamente.
1. **Separación clara de capas**: Se define una estructura de paquetes que refleja la arquitectura en capas.
