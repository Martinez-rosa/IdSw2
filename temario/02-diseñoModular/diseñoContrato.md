# Diseño por contrato

## ¿Por qué?

El desarrollo de software fiable enfrenta constantemente el desafío de asegurar el comportamiento correcto de los componentes y sus interacciones. Cuando las expectativas entre los proveedores y consumidores de servicios no están claramente definidas y verificadas, surgen numerosos problemas que afectan la calidad del sistema.

Estas deficiencias en la formalización de expectativas se manifiestan en diversos problemas:

- **Errores silenciosos**: Comportamientos incorrectos que pasan desapercibidos hasta etapas avanzadas del desarrollo o incluso producción, multiplicando el costo de su corrección.

- **Programación defensiva**: Código sobrecargado con verificaciones redundantes en múltiples lugares, aumentando la complejidad y reduciendo la legibilidad.

```java
// Ejemplo de programación defensiva
public void procesarPedido(Pedido pedido) {
    // Verificación en el método llamador
    if (pedido == null) {
        throw new IllegalArgumentException("El pedido no puede ser nulo");
    }
    if (pedido.getCliente() == null) {
        throw new IllegalArgumentException("El cliente del pedido no puede ser nulo");
    }
    if (pedido.getItems() == null || pedido.getItems().isEmpty()) {
        throw new IllegalArgumentException("El pedido debe contener al menos un ítem");
    }
    
    // Lógica de negocio...
    for (Item item : pedido.getItems()) {
        // Más verificaciones defensivas
        if (item == null) {
            throw new IllegalArgumentException("El ítem no puede ser nulo");
        }
        if (item.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser positiva");
        }
        
        // Procesamiento del ítem...
    }
    
    // Más código...
}
```

- **Responsabilidades ambiguas**: Confusión sobre quién debe verificar la validez de los datos, llevando a situaciones donde:
  - Nadie realiza la verificación, causando errores
  - Múltiples componentes realizan las mismas verificaciones, creando redundancia

- **Propagación de estados inválidos**: Valores incorrectos que se propagan a través del sistema, causando fallos en componentes alejados del origen del problema.

- **Documentación insuficiente**: Comentarios y documentación informal que no especifica claramente las condiciones requeridas para la correcta operación de un componente.

- **Pruebas incompletas**: Dificultad para diseñar casos de prueba exhaustivos sin conocer formalmente las restricciones y garantías de los componentes.

Un caso típico que ilustra estos problemas es la siguiente situación: un método divide dos números sin verificar si el divisor es cero, asumiendo que el código llamante habrá realizado esta verificación. El código llamante, por su parte, asume que el método manejará adecuadamente los divisores inválidos. El resultado es un error en tiempo de ejecución que podría haberse evitado con una definición clara de responsabilidades.

## ¿Qué?

El Diseño por Contrato (Design by Contract™ o DbC) es un enfoque de desarrollo de software introducido por Bertrand Meyer en el lenguaje de programación Eiffel, que formaliza las obligaciones mutuas entre componentes mediante la especificación explícita de precondiciones, postcondiciones e invariantes.

> "Considerar las relaciones entre una clase y sus clientes como un contrato formal expresando los derechos y las obligaciones de cada parte."
>
> — Bertrand Meyer

Este enfoque proporciona un marco sistemático para definir y verificar el comportamiento esperado de los componentes, tratando sus interacciones como contratos formales que establecen:

- Lo que el cliente debe garantizar antes de invocar una operación
- Lo que el proveedor garantiza después de ejecutar la operación
- Las propiedades que siempre se mantienen durante la vida del objeto

### Elementos del contrato

#### Precondiciones

Las precondiciones especifican las restricciones que deben cumplirse antes de que un método pueda ejecutarse correctamente. Representan las obligaciones del cliente (código llamante) y los beneficios para el servidor (código llamado).

```java
/**
 * Calcula la raíz cuadrada de un número.
 *
 * @param numero El número para calcular su raíz cuadrada
 * @return La raíz cuadrada del número
 * @pre numero >= 0
 */
public double raizCuadrada(double numero) {
    assert numero >= 0 : "La precondición 'numero >= 0' ha sido violada";
    
    // Implementación asumiendo que numero es no negativo
    return Math.sqrt(numero);
}
```

Las precondiciones:

- Son responsabilidad del cliente verificarlas antes de la llamada
- Liberan al servidor de manejar casos fuera del contrato
- Deben ser comprobables antes de la ejecución del método

#### Postcondiciones

Las postcondiciones especifican las garantías que proporciona un método después de su ejecución exitosa. Representan las obligaciones del servidor y los beneficios para el cliente.

```java
/**
 * Busca un elemento en una colección ordenada.
 *
 * @param coleccion La colección ordenada donde buscar
 * @param elemento El elemento a buscar
 * @return El índice del elemento en la colección, o -1 si no se encuentra
 * @post resultado >= -1 && resultado < coleccion.length
 * @post resultado != -1 implica coleccion[resultado] == elemento
 * @post resultado == -1 implica !existe i: coleccion[i] == elemento
 */
public int busquedaBinaria(int[] coleccion, int elemento) {
    // Implementación...
    int resultado = /* cálculo */;
    
    assert resultado >= -1 && resultado < coleccion.length : 
        "La postcondición 'resultado >= -1 && resultado < coleccion.length' ha sido violada";
    assert resultado == -1 || coleccion[resultado] == elemento : 
        "La postcondición 'resultado != -1 implica coleccion[resultado] == elemento' ha sido violada";
    
    return resultado;
}
```

Las postcondiciones:

- Son responsabilidad del servidor garantizarlas
- Definen los resultados esperados del método
- Pueden referirse al estado previo a la ejecución (con notaciones como "old" en Eiffel)

#### Invariantes de clase

Los invariantes de clase especifican condiciones que deben mantenerse durante toda la vida de un objeto, tanto antes como después de cualquier operación pública. Representan la consistencia interna que la clase debe mantener.

```java
/**
 * Representa una pila con capacidad limitada.
 * 
 * @inv 0 <= tamaño <= capacidad
 * @inv elementos no es null
 */
public class PilaAcotada<T> {
    private final Object[] elementos;
    private final int capacidad;
    private int tamaño;
    
    public PilaAcotada(int capacidadMax) {
        assert capacidadMax > 0 : "La capacidad debe ser positiva";
        
        this.capacidad = capacidadMax;
        this.elementos = new Object[capacidad];
        this.tamaño = 0;
        
        verificarInvariante();
    }
    
    /**
     * Añade un elemento a la pila.
     * 
     * @param elemento El elemento a añadir
     * @pre !estaLlena()
     * @post tamaño == old(tamaño) + 1
     * @post cima() == elemento
     */
    public void apilar(T elemento) {
        verificarInvariante();
        assert !estaLlena() : "Precondición violada: la pila está llena";
        
        elementos[tamaño++] = elemento;
        
        assert tamaño == old + 1 : "Postcondición violada: tamaño no incrementó correctamente";
        assert cima() == elemento : "Postcondición violada: el elemento no está en la cima";
        verificarInvariante();
    }
    
    private void verificarInvariante() {
        assert tamaño >= 0 && tamaño <= capacidad : "Invariante violado: 0 <= tamaño <= capacidad";
        assert elementos != null : "Invariante violado: elementos no es null";
    }
    
    // Otros métodos...
}
```

Los invariantes:

- Deben ser verdaderos después de la construcción del objeto
- Deben mantenerse antes y después de cada método público
- Pueden estar temporalmente violados dentro de los métodos privados

### Características del enfoque DbC

#### Enfoque en la corrección

El DbC se centra en asegurar la corrección del software, considerando los contratos como una especificación formal del comportamiento esperado.

#### Distinción de responsabilidades

Establece una clara distinción entre:

- **Error lógico**: Violación de una precondición, postcondición o invariante dentro de la especificación del programa
- **Error excepcional**: Situación fuera de la especificación (fallo del sistema, error de comunicación, etc.)

#### Herramientas de implementación

Se implementa mediante:

- **Aserciones**: Para verificar contratos en tiempo de ejecución
- **Anotaciones**: Para documentar contratos estáticamente
- **Herramientas de análisis**: Para verificar contratos en tiempo de compilación

## ¿Para qué?

La aplicación sistemática del Diseño por Contrato produce sistemas con las siguientes características positivas:

- **Corrección mejorada**: Detección temprana de violaciones de contrato durante el desarrollo y pruebas.
- **Claridad en responsabilidades**: Definición precisa de qué componente debe garantizar cada condición.
- **Documentación ejecutable**: Los contratos sirven simultáneamente como documentación y como verificaciones en tiempo de ejecución.
- **Simplificación del código**: Eliminación de verificaciones redundantes y código de manejo de errores innecesario.
- **Pruebas dirigidas**: Contratos que guían el diseño de casos de prueba para cubrir específicamente los límites definidos.

<div align=center>

| Diseño por Contrato Efectivo |||| Ausencia de Contratos Formales |
|-|-:|:-:|:-|-|
|Errores detectados tempranamente  |**Robustez**|     *vs*|**Fragilidad**  | Errores propagados y detectados tardíamente |
|Responsabilidades claras          |**Fluidez**|*vs*|**Viscosidad**| Confusión sobre quién verifica qué |
|Código limpio y directo           |**Flexibilidad**|*vs*|**Rigidez**| Verificaciones redundantes en múltiples lugares |
|Documentación precisa y verificable|**Reusabilidad**|    *vs*|**Inmovilidad**  | Comportamiento poco documentado o no verificado |

</div>

La metáfora del "contrato legal" captura perfectamente este principio:

> "Así como un contrato legal define claramente las obligaciones y garantías entre las partes, reduciendo malentendidos y litigios, el Diseño por Contrato define formalmente las responsabilidades entre componentes de software, minimizando errores y facilitando la detección de violaciones en las primeras etapas del desarrollo."

## ¿Cómo?

Para aplicar efectivamente el Diseño por Contrato en el desarrollo de software, se pueden seguir estas estrategias prácticas:

### Establecer una estrategia de contratos

#### Elegir el nivel de aplicación

Decidir cómo se implementarán los contratos:

##### Nivel documentación

Contratos expresados en comentarios y documentación.

  ```java
  /**
   * Divide dos números.
   * 
   * @param dividendo El número a dividir
   * @param divisor El número por el cual dividir
   * @return El resultado de la división
   * @throws IllegalArgumentException si divisor es cero
   * @pre divisor != 0
   * @post Math.abs(resultado * divisor - dividendo) < 0.0001
   */
  public double dividir(double dividendo, double divisor) {
      if (divisor == 0) {
          throw new IllegalArgumentException("El divisor no puede ser cero");
      }
      return dividendo / divisor;
  }
  ```

##### Nivel verificación en runtime

Contratos verificados durante la ejecución.

  ```java
  public double dividir(double dividendo, double divisor) {
      // Verificación de precondición
      assert divisor != 0 : "Precondición violada: divisor != 0";
      
      double resultado = dividendo / divisor;
      
      // Verificación de postcondición
      assert Math.abs(resultado * divisor - dividendo) < 0.0001 : 
          "Postcondición violada: resultado inconsistente";
      
      return resultado;
  }
  ```

##### Nivel verificación estática

Contratos verificados en tiempo de compilación.

  ```java
  // Usando JML (Java Modeling Language)
  //@ requires divisor != 0;
  //@ ensures Math.abs(\result * divisor - dividendo) < 0.0001;
  public double dividir(double dividendo, double divisor) {
      return dividendo / divisor;
  }
  ```

#### Establecer convenciones

Definir estándares para la expresión de contratos:

- Formato consistente para documentación
- Nomenclatura para aserciones
- Ubicación de verificaciones en el código

### Implementar contratos de manera progresiva

#### Identificar componentes críticos

Comenzar aplicando contratos en:

- Interfaces de módulos principales
- Algoritmos complejos
- Código de manejo de datos sensibles
- Puntos de integración entre componentes

#### Definir precondiciones

Especificar claramente qué debe cumplirse antes de llamar a un método:

```java
/**
 * Transfiere fondos entre cuentas.
 * 
 * @param origen Cuenta de origen
 * @param destino Cuenta de destino
 * @param monto Cantidad a transferir
 * @pre origen != null
 * @pre destino != null
 * @pre monto > 0
 * @pre origen.getSaldo() >= monto
 */
public void transferir(Cuenta origen, Cuenta destino, double monto) {
    // Verificación de precondiciones
    Objects.requireNonNull(origen, "Cuenta origen no puede ser nula");
    Objects.requireNonNull(destino, "Cuenta destino no puede ser nula");
    if (monto <= 0) {
        throw new IllegalArgumentException("El monto debe ser positivo");
    }
    if (origen.getSaldo() < monto) {
        throw new IllegalArgumentException("Saldo insuficiente");
    }
    
    // Implementación...
}
```

#### Definir postcondiciones

Especificar las garantías que ofrece el método después de su ejecución:

```java
/**
 * Busca un cliente en la base de datos.
 * 
 * @param id Identificador del cliente
 * @return Cliente encontrado o null si no existe
 * @post resultado == null || resultado.getId().equals(id)
 */
public Cliente buscarPorId(String id) {
    Cliente cliente = repositorio.buscar(id);
    
    // Verificación de postcondición
    assert cliente == null || cliente.getId().equals(id) : 
        "Postcondición violada: ID del cliente inconsistente";
    
    return cliente;
}
```

#### Definir invariantes

Especificar condiciones que deben mantenerse durante toda la vida del objeto:

```java
/**
 * Representa una cuenta bancaria.
 * 
 * @inv saldo >= saldoMinimo
 * @inv numeroCuenta != null && !numeroCuenta.isEmpty()
 */
public class CuentaBancaria {
    private final String numeroCuenta;
    private double saldo;
    private final double saldoMinimo;
    
    // Constructor, métodos...
    
    private void verificarInvariante() {
        assert saldo >= saldoMinimo : 
            "Invariante violado: saldo >= saldoMinimo";
        assert numeroCuenta != null && !numeroCuenta.isEmpty() : 
            "Invariante violado: numeroCuenta válido";
    }
}
```

### Utilizar herramientas y técnicas de implementación

#### Aserciones nativas

Usar las capacidades de aserción del lenguaje:

```java
// Java assert
assert condicion : "Mensaje de error si la condición es falsa";

// C# Debug.Assert
Debug.Assert(condicion, "Mensaje de error");

// C++ assert
assert(condicion && "Mensaje de error");
```

#### Bibliotecas de contratos

Utilizar bibliotecas especializadas:

```java
// Java con Google Guava
Preconditions.checkArgument(monto > 0, "El monto debe ser positivo");
Preconditions.checkNotNull(cliente, "El cliente no puede ser nulo");

// C# Code Contracts
Contract.Requires(monto > 0);
Contract.Ensures(Contract.Result<double>() >= 0);

// Python con PyContracts
@contract(input='array,!empty', result='float,>=0')
def calcularPromedio(array):
    return sum(array) / len(array)
```

#### Frameworks de validación

Integrar con frameworks de validación:

```java
// Java con Bean Validation
public class Pedido {
    @NotNull
    private Cliente cliente;
    
    @NotEmpty
    private List<Item> items;
    
    @Min(1)
    private int cantidad;
    
    // Getters, setters...
}

// Validación
ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
Validator validator = factory.getValidator();
Set<ConstraintViolation<Pedido>> violaciones = validator.validate(pedido);
```

#### Aspectos (AOP)

Utilizar programación orientada a aspectos para verificaciones:

```java
// AspectJ para verificar precondiciones
@Aspect
public class ContractAspect {
    @Before("execution(* transferir(Cuenta, Cuenta, double)) && args(origen, destino, monto)")
    public void verificarPrecondicionesTransferencia(Cuenta origen, Cuenta destino, double monto) {
        if (origen == null || destino == null) {
            throw new IllegalArgumentException("Las cuentas no pueden ser nulas");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        if (origen.getSaldo() < monto) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
    }
}
```

### Balancear contratos y excepciones

#### Distinguir entre errores contractuales y excepcionales

- **Errores contractuales**: Violaciones de precondiciones, postcondiciones o invariantes (generalmente problemas de programación).
- **Errores excepcionales**: Situaciones fuera del control del programa (problemas externos).

```java
public File abrirArchivo(String ruta) {
    // Verificación de precondición (error contractual)
    if (ruta == null || ruta.isEmpty()) {
        throw new IllegalArgumentException("La ruta no puede ser nula o vacía");
    }
    
    try {
        // Posible error excepcional (archivo no existe, sin permisos, etc.)
        return new File(ruta);
    } catch (SecurityException e) {
        throw new IOException("No se puede acceder al archivo por restricciones de seguridad", e);
    }
}
```

#### Estrategias de verificación

Decidir cuándo y cómo verificar contratos:

- **Desarrollo/Pruebas**: Verificación agresiva y completa de todos los contratos.
- **Producción**: Verificación selectiva o desactivación por rendimiento.

```java
public class Configuracion {
    private static final boolean VERIFICAR_CONTRATOS = 
        Boolean.parseBoolean(System.getProperty("app.verificarContratos", "true"));
    
    public static void verificarPrecondicion(boolean condicion, String mensaje) {
        if (VERIFICAR_CONTRATOS && !condicion) {
            throw new PrecondicionVioladaException(mensaje);
        }
    }
    
    // Métodos similares para postcondiciones e invariantes...
}

// Uso
public void metodo(Parametro p) {
    Configuracion.verificarPrecondicion(p != null, "Parámetro no puede ser nulo");
    // Implementación...
}
```

### Integrar con el ciclo de desarrollo

#### Pruebas basadas en contratos

Diseñar casos de prueba específicamente para verificar contratos:

```java
@Test
public void transferir_MontoNegativo_LanzaExcepcion() {
    Cuenta origen = new Cuenta("123", 1000);
    Cuenta destino = new Cuenta("456", 500);
    
    // Prueba de precondición: monto > 0
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        servicio.transferir(origen, destino, -100);
    });
    
    assertTrue(exception.getMessage().contains("monto"));
}
```

#### Documentación generada

Extraer contratos para generar documentación:

```java
// Usando herramientas como Javadoc, Doxygen o documentadores personalizados
// que interpreten anotaciones de contrato

/**
 * Transfiere fondos entre cuentas.
 * 
 * @param origen Cuenta de origen
 * @param destino Cuenta de destino
 * @param monto Cantidad a transferir
 * @pre origen != null
 * @pre destino != null
 * @pre monto > 0
 * @pre origen.getSaldo() >= monto
 * @post origen.getSaldo() == old(origen.getSaldo()) - monto
 * @post destino.getSaldo() == old(destino.getSaldo()) + monto
 */
public void transferir(Cuenta origen, Cuenta destino, double monto) {
    // Implementación...
}
```

#### Análisis estático

Incorporar herramientas de análisis estático para verificar contratos:

- ESC/Java para Java
- Spec# para C#
- Frama-C para C
- CodeContracts para .NET
