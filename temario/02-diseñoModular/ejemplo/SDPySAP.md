# Sistema de gestión de pagos de tienda online

## Capas

### Superior

Capa más *volátil*.

```java
package com.tienda.compras;

public class ServicioCompras {
    private final ProcesadorPago procesadorPago;
    
    public ServicioCompras(ProcesadorPago procesadorPago) {
        this.procesadorPago = procesadorPago;
    }
    
    public boolean completarCompra(Carrito carrito, DatosCliente cliente) {
        Transaccion transaccion = crearTransaccion(carrito, cliente);
        return procesadorPago.procesar(transaccion);
    }
    
    private Transaccion crearTransaccion(Carrito carrito, DatosCliente cliente) {
        // Lógica para crear la transacción
        return new Transaccion();
    }
}
```

## Intermedia

Implementaciones concretas

```java
package com.tienda.pagos.proveedores;

public class ProcesadorPayPal implements ProcesadorPago {
    @Override
    public boolean procesar(Transaccion transaccion) {
        // Lógica específica para procesar pagos con PayPal
        return true;
    }
    
    @Override
    public EstadoPago verificarEstado(String idTransaccion) {
        // Lógica específica para verificar pagos con PayPal
        return EstadoPago.COMPLETADO;
    }
    
    @Override
    public void reembolsar(String idTransaccion) {
        // Lógica específica para reembolsos con PayPal
    }
}

public class ProcesadorStripe implements ProcesadorPago {
    // Implementación similar para Stripe
}
```

## Inferior

Capa estable. Nótese como propone interfaces que luego implementan las capas superiores.

```java
package com.tienda.pagos.core;

public interface ProcesadorPago {
    boolean procesar(Transaccion transaccion);
    EstadoPago verificarEstado(String idTransaccion);
    void reembolsar(String idTransaccion);
}

public class Transaccion {
    private String id;
    private double monto;
    private String moneda;
    // Getters y setters
}

public enum EstadoPago {
    PENDIENTE, COMPLETADO, FALLIDO, REEMBOLSADO
}
```

|Principio de dependencias estables|Principio de abstracciones estables|
|-|-|
|La dependencia fluye desde los componentes más volátiles (ServicioCompras) hacia los más estables (ProcesadorPago).|El componente más estable (ProcesadorPago) es una interfaz abstracta.|
|El código de aplicación (ServicioCompras) depende de la abstracción estable, no de implementaciones concretas.|No contiene lógica concreta, solo define el contrato.|
|Las implementaciones específicas de pago pueden cambiar sin afectar al ServicioCompras.|Permite extensibilidad: podemos agregar nuevos procesadores de pago (como ProcesadorBitcoin) sin modificar la interfaz ni el código cliente.|

## Supuestos *#2Think*

- Si agregamos un nuevo método a la interfaz ProcesadorPago, afectaría a todas las implementaciones.
- Si cambiamos la implementación de ProcesadorPayPal, solo afecta a ese componente.
- El ServicioCompras no necesita saber qué procesador de pago está usando, solo trabaja con la abstracción.

Por tanto:

- Podemos agregar nuevos procesadores de pago sin tocar el código de la aplicación.
- Podemos modificar la lógica interna de un procesador sin afectar otros componentes.
- La interfaz estable cambia con poca frecuencia, mientras las implementaciones pueden evolucionar independientemente.