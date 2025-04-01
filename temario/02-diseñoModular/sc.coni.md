# Comportamiento obvio no implementado

Interfaces que omiten operaciones que los clientes razonablemente esperarían.

Esto viola el "Principio de Menor Sorpresa" del diseño de software, el cual establece que los componentes de un sistema deben comportarse de manera que minimice la confusión entre los usuarios del sistema.

## Ejemplo

### Problema

```java
// Problema: Operación obvia faltante
interface Coleccion<T> {
    void agregar(T elemento);
    T obtener(int indice);
    // Falta método para eliminar elementos
}
```

### Solución propuesta

```java
// Solución: Incluir operación esperada
interface Coleccion<T> {
    void agregar(T elemento);
    T obtener(int indice);
    void eliminar(int indice);
}
```
