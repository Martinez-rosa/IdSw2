# Acoplamiento

## ¿Por qué?

El diseño de software frecuentemente se enfrenta a problemas derivados de la excesiva dependencia entre sus componentes. Cuando un elemento del sistema conoce demasiado sobre otros elementos o depende de muchos de ellos, se genera un escenario donde cualquier modificación en un componente puede provocar una cascada de cambios en otros componentes dependientes.

Esta situación produce sistemas que presentan las siguientes características negativas:

- **Rigidez**: No se puede modificar con facilidad porque un simple cambio puede requerir modificaciones en cadena a través de múltiples componentes, elevando el coste y riesgo de cada modificación.
- **Fragilidad**: No se puede probar con facilidad porque cada cambio puede provocar fallos en partes del sistema aparentemente no relacionadas. Las modificaciones generan errores en lugares inesperados.
- **Inmovilidad**: No se puede reutilizar con facilidad porque los componentes están fuertemente ligados a su contexto original. Extraer una funcionalidad para usarla en otro sistema requiere llevarse también todas sus dependencias.
- **Viscosidad**: No se puede entender con facilidad porque para comprender un componente es necesario conocer también todos aquellos de los que depende, sobrecargando la capacidad cognitiva del desarrollador.

Las manifestaciones típicas de este problema incluyen clases con múltiples importaciones, métodos con numerosos parámetros, y objetos que acceden directamente a atributos o métodos internos de otros objetos.

## ¿Qué?

El acoplamiento es una métrica fundamental del diseño de software que evalúa el grado de interdependencia entre los componentes de un sistema.

> "El acoplamiento es una medida de la fuerza con un elemento está conectado a, tiene conocimiento de, o se basa en otros elementos. Estos elementos incluyen sistemas, paquetes, clases y métodos."
>
> — Craig Larman, UML y Patrones

Este concepto, junto con la cohesión, fue introducido por Larry Constantine en la década de 1960 y desarrollado posteriormente con Ed Yourdon y Wayne Stevens como parte de los fundamentos del diseño estructurado.

### Tipos de acoplamiento

Se pueden distinguir diferentes categorías de acoplamiento:

#### Según la dirección

|Aferente||Eferente|
|-|-|-|
|Mide cuántas clases dependen de una clase específica. Un alto valor indica una clase ampliamente utilizada en el sistema, lo que puede reflejar una buena reutilización pero también un punto crítico de fragilidad.|![](/images/modelosUML/acoplamiento.svg)|Mide cuántas clases son utilizadas por una clase específica. Un alto valor indica una clase que depende de muchos otros componentes, lo que reduce su independencia y aumenta su fragilidad ante cambios.

#### Según la forma

1. **Directo**: Se establece cuando una clase hace referencia explícita a otra. Puede darse a través de:

   - Atributos de la clase
   - Parámetros en métodos
   - Variables locales
   - Tipos de retorno
   - Herencia
   
   ```java
   // Acoplamiento directo por atributo
   public class Pedido {
       private Cliente cliente; // Acoplamiento directo a Cliente
   }
   
   // Acoplamiento directo por parámetro
   public void procesarPago(MetodoPago metodoPago) {
       // Acoplamiento directo a MetodoPago
   }
   
   // Acoplamiento directo por herencia
   public class VehiculoElectrico extends Vehiculo {
       // Acoplamiento directo a Vehiculo
   }
   ```

1. **Indirecto**: Se produce cuando una clase accede a otra a través de una cadena de intermediarios.
   
   ```java
   public class Empleado {
       private Departamento departamento;
       
       public Empresa getEmpresa() {
           return departamento.getEmpresa(); // Acoplamiento indirecto a Empresa
       }
   }
   ```

1. **De contenido**: Ocurre cuando un módulo modifica directamente el contenido interno de otro módulo.
   
   ```java
   // Acoplamiento de contenido (muy alto y no recomendado)
   public class SistemaArchivos {
       public void guardarArchivo(Documento doc) {
           doc.contenido = formatearContenido(doc.contenido); // Modificación directa
       }
   }
   ```
   
1. **Común**: Surge cuando múltiples componentes dependen de un recurso compartido, como una variable global o una base de datos.
1. **Temporal**: Se produce cuando varios componentes deben ejecutarse en un orden específico.

#### Según la intensidad

1. **Alto**: Se caracteriza por un conocimiento detallado entre componentes, típicamente a nivel de implementación.
1. **Bajo**: Los componentes interactúan a través de interfaces bien definidas sin conocer detalles internos de implementación.

### Niveles de acoplamiento

El grado de acoplamiento puede clasificarse en varios niveles, ordenados de mejor a peor:

1. **Por mensaje** (el más bajo): Los componentes se comunican a través de mensajes simples sin compartir estructura de datos.
2. **Por interfaz**: Los componentes se comunican a través de interfaces bien definidas y estables.
   
   ```java
   // Acoplamiento por interfaz (bajo)
   public interface Notificador {
       void enviarNotificacion(String mensaje, String destinatario);
   }
   
   public class ServicioAlerta {
       private Notificador notificador;
       
       public ServicioAlerta(Notificador notificador) {
           this.notificador = notificador;
       }
       
       public void alertar(String mensaje, String destinatario) {
           notificador.enviarNotificacion(mensaje, destinatario);
       }
   }
   ```

3. **Por datos**: Los componentes comparten datos a través de parámetros, pero no comparten su estructura.
4. **Por control**: Un componente controla el flujo de ejecución de otro.
5. **Por estructura externa**: Los componentes comparten un formato de datos, protocolo o interfaz con un elemento externo.
6. **Por estructura interna**: Un componente depende de la implementación interna de otro.
7. **Por contenido** (el más alto): Un componente modifica directamente el estado interno de otro.

### Principio de la Ley de Demeter (LoD)

La Ley de Demeter, también conocida como "Principio del Menor Conocimiento" o "No hables con extraños", es una guía para reducir el acoplamiento:

> Un método de un objeto solo debe llamar a métodos pertenecientes a:
> 1. Sí mismo
> 2. Sus parámetros
> 3. Cualquier objeto que cree
> 4. Sus atributos directos

Ejemplos de violación de la Ley de Demeter:

```java
// Violación de la Ley de Demeter - "Encadenamiento de trenes"
cliente.getDireccion().getCiudad().getCodigoPostal();

// Forma correcta - Respetar la Ley de Demeter
cliente.getCodigoPostal();
```

### Círculo virtuoso: acoplamiento y cohesión

Existe una relación sinérgica entre el bajo acoplamiento y la alta cohesión:

- Al reducir el acoplamiento, delegando responsabilidades a los objetos apropiados, generalmente se aumenta la cohesión.
- Al aumentar la cohesión, asumiendo menos responsabilidades por componente, generalmente se reduce el acoplamiento.

## ¿Para qué?

La aplicación deliberada del principio de bajo acoplamiento en el diseño de software produce sistemas con las siguientes características positivas:

- **Fluidez** porque son fáciles de comprender
- **Flexible** porque son fáciles de modificar
- **Reusable** porque son fáciles de reutilizar
- **Robustos** porque no están constantemente afectados por el cambio

Las ventajas del bajo acoplamiento frente al alto acoplamiento resultan evidentes al comparar ambos enfoques:

<div align=center>

| Bajo Acoplamiento |||| Alto Acoplamiento |
|-|-:|:-:|:-|-|
|Cambios localizados                |**Flexibilidad**|     *vs*|**Rigidez**  | Cambios en cascada |
|Fácil de entender                  |**Fluidez**|*vs*|**Viscosidad**| Difícil de comprender |
|Componentes reutilizables          |**Reusabilidad**|*vs*|**Inmovilidad**| Componentes inmóviles |
|Resistente a efectos secundarios   |**Robustez**|    *vs*|**Fragilidad**  | Propenso a fallos inesperados |

</div>

Como analogía para comprender la importancia del acoplamiento, puede utilizarse la del sistema eléctrico:

> "Un sistema con alto acoplamiento es como una instalación eléctrica donde todos los cables están entrelazados y sin etiquetas: cualquier modificación requiere revisar todo el cableado. Un sistema con bajo acoplamiento es como una instalación modular con conectores estandarizados y bien etiquetados: cada componente puede reemplazarse o modificarse sin afectar al resto."

## ¿Cómo?

Para aplicar correctamente el principio de bajo acoplamiento en el diseño de software, deben seguirse estas estrategias prácticas:

### Identificar "code smells" que señalan problemas de acoplamiento

- **[Intimidad inapropiada](sc.ii.md)**: Cuando una clase usa excesivamente los detalles internos de otra clase.
- **[Librería incompleta](sc.ilc.md)**: Cuando se añaden métodos a clases para compensar limitaciones en bibliotecas existentes.
- **[Envidia de características](sc.fe.md)**: Un método que parece más interesado en una clase diferente a la que pertenece.
- **[Intermediario](sc.mm.md)**: Una clase que simplemente delega a otra.
- **[Principios de paquetes quebrados](sc.bpp.md)**: Paquetes con dependencias cíclicas o excesivas.
- **[Cadena de mensajes](sc.mc.md)** [***E·Ext***](https://www.metridev.com/metrics/message-chain-code-smells-how-to-identify-and-fix-them/): Series de llamadas del tipo `a.getB().getC().getD()`.
- **Herencia rechazada** [***E·Ext***](https://swiftlynomad.medium.com/code-smells-object-orientation-abusers-refused-bequest-4f1f23b91acc): Subclases que no usan o sobrescriben gran parte de lo que heredan.

### Técnicas de diseño & refactorización

- **Ley de demeter**: Se debe limitar la comunicación solo a "amigos cercanos".

```java
// Violación de la Ley de Demeter
public class GeneradorFacturas {
    public void generarFactura(Pedido pedido) {
        String direccion = pedido.getCliente().getDireccion().getCalle() + ", " +
                           pedido.getCliente().getDireccion().getCiudad();
        // ...
    }
}

// Respetando la Ley de Demeter
public class GeneradorFacturas {
    public void generarFactura(Pedido pedido) {
        String direccion = pedido.getDireccionEntrega();
        // ...
    }
}

public class Pedido {
    private Cliente cliente;
    
    public String getDireccionEntrega() {
        return cliente.getDireccionCompleta();
    }
}

public class Cliente {
    private Direccion direccion;
    
    public String getDireccionCompleta() {
        return direccion.getFormatoCompleto();
    }
}
```

- **Tell, Don't Ask**: Se debe indicar a los objetos qué hacer en lugar de pedirles información para luego actuar sobre ella.

```java
// Estilo "Ask" - Alto acoplamiento
public void procesarPedido(Pedido pedido) {
    if (pedido.getEstado() == EstadoPedido.NUEVO) {
        pedido.setEstado(EstadoPedido.PROCESANDO);
        // Más lógica...
    }
}

// Estilo "Tell" - Bajo acoplamiento
public void procesarPedido(Pedido pedido) {
    pedido.procesar();
}
```

- **Programación contra interfaces**: Se debe depender de abstracciones en lugar de implementaciones concretas.

```java
// Alto acoplamiento - Dependencia de implementación concreta
public class ServicioNotificacion {
    private NotificadorEmail notificador = new NotificadorEmail();
    
    public void alertar(Usuario usuario, String mensaje) {
        notificador.enviarEmail(usuario.getEmail(), mensaje);
    }
}

// Bajo acoplamiento - Dependencia de abstracción
public class ServicioNotificacion {
    private Notificador notificador; // Interfaz
    
    public ServicioNotificacion(Notificador notificador) {
        this.notificador = notificador;
    }
    
    public void alertar(Usuario usuario, String mensaje) {
        notificador.notificar(usuario, mensaje);
    }
}
```

- **Dependencias**: Las dependencias deben ser proporcionadas desde el exterior en lugar de ser creadas internamente.

```java
// Alto acoplamiento - Creación interna de dependencias
public class ProcesadorPedidos {
    private RepositorioPedidos repositorio = new RepositorioPedidosMySQL();
    private ServicioEnvios envios = new ServicioEnviosExpress();
    
    // Métodos...
}

// Bajo acoplamiento - Inyección de dependencias
public class ProcesadorPedidos {
    private final RepositorioPedidos repositorio;
    private final ServicioEnvios envios;
    
    public ProcesadorPedidos(RepositorioPedidos repositorio, ServicioEnvios envios) {
        this.repositorio = repositorio;
        this.envios = envios;
    }
    
    // Métodos...
}
```

### Métricas para evaluar el acoplamiento

Pueden emplearse métricas formales para medir el acoplamiento:

- **Acoplamiento Aferente (Ca)**: Número de clases fuera del componente que dependen de clases dentro del componente.
- **Acoplamiento Eferente (Ce)**: Número de clases dentro del componente que dependen de clases fuera del componente.
- **Inestabilidad (I)**: Calculada como Ce/(Ca+Ce), varía de 0 (máxima estabilidad) a 1 (máxima inestabilidad).
- **Abstractness (A)**: Proporción de clases abstractas en un componente.
- **Distancia de la secuencia principal**: |A+I-1|, donde valores cercanos a 0 indican un equilibrio entre abstracción y estabilidad.

### Herramientas de análisis

Se recomienda implementar herramientas como JDepend, SonarQube, Structure101, o NDepend que pueden analizar el acoplamiento y proporcionar visualizaciones de las dependencias.
