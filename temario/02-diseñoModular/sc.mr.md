# Misplaced Responsibility

Operaciones ubicadas en componentes inapropiados.

## Ejemplo

### Problema

```java
class Cliente {
    // ...
    public void guardarEnBaseDatos() {
        // Lógica de persistencia dentro de la entidad
    }
}
```

### Solución propuesta

```java
class Cliente {

}

interface RepositorioCliente {
    void guardar(Cliente cliente);
    Cliente buscarPorId(String id);
    List<Cliente> buscarTodos();
    void eliminar(String id);
}

class RepositorioClienteMySQL implements RepositorioCliente {
    @Override
    public void guardar(Cliente cliente) {
        // Implementación de persistencia
    }
    
    @Override
    public Cliente buscarPorId(String id) {
        // Implementación
    }
    
    @Override
    public List<Cliente> buscarTodos() {
        // Implementación
    }
    
    @Override
    public void eliminar(String id) {
        // Implementación
    }
}
```
