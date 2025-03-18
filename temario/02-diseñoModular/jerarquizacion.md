# Jerarquización

## ¿Por qué?

El desarrollo de sistemas complejos presenta un desafío fundamental: la capacidad humana para comprender y manejar información tiene límites cognitivos bien documentados. Cuando un sistema software alcanza cierto tamaño y complejidad, se vuelve imposible entenderlo como un todo indiferenciado.

Esta barrera cognitiva se manifiesta en varios problemas:

- **Sobrecarga cognitiva**: Los desarrolladores no pueden mantener en su mente todas las interacciones y dependencias del sistema simultáneamente, lo que lleva a errores y omisiones.
- **Dependencias desordenadas**: Sin una estructura jerárquica clara, las dependencias entre componentes pueden extenderse en todas direcciones, creando una red caótica de relaciones.
- **Complejidad horizontal**: Los sistemas que crecen "en ancho" sin niveles de abstracción sufren de proliferación de componentes interdependientes sin una estructura comprensible.
- **Dificultades de navegación**: Sin niveles jerárquicos, localizar el código responsable de determinada funcionalidad se convierte en una búsqueda exhaustiva.
- **Imposibilidad de evolución independiente**: Un sistema sin una jerarquía adecuada no permite que distintas partes evolucionen a diferentes ritmos según sus necesidades específicas.

Estos problemas se evidencian en sistemas donde todas las clases interactúan potencialmente con cualquier otra, donde no hay un "mapa" claro de la organización del código, o donde la adición de una nueva funcionalidad requiere modificaciones en múltiples lugares sin un patrón discernible.

## ¿Qué?

La jerarquización es un principio fundamental del diseño de sistemas complejos que propone la organización de los componentes en niveles de abstracción bien definidos, con relaciones de dependencia controladas principalmente en dirección vertical.

> "La jerarquía, o la organización de componentes en diferentes niveles de abstracción con relaciones claramente definidas entre niveles, es uno de los mecanismos fundamentales que utilizamos para manejar la complejidad."
>
> — Herbert Simon, "Las ciencias de lo artificial"

Este principio se basa en observaciones de sistemas complejos tanto naturales como artificiales, donde la estructura jerárquica emerge como una estrategia evolutiva exitosa para gestionar la complejidad.

### Enfoques de jerarquización

#### Diseño descendente (top-down)

El enfoque descendente comienza con una visión de alto nivel del sistema y procede metódicamente hacia los detalles de implementación:

1. Se identifica el problema global que debe resolverse.
2. Se descompone en subproblemas más manejables.
3. Se continúa la descomposición hasta llegar a componentes lo suficientemente simples para ser implementados directamente.

```
Sistema
├── Subsistema A
│   ├── Módulo A.1
│   │   ├── Clase A.1.1
│   │   └── Clase A.1.2
│   └── Módulo A.2
└── Subsistema B
    ├── Módulo B.1
    └── Módulo B.2
```

Este enfoque es análogo a la técnica de "divide y vencerás" y resulta particularmente eficaz cuando:

- El dominio del problema es bien comprendido
- Se requiere una visión coherente del sistema completo
- La arquitectura global es crítica para el éxito del proyecto

#### Diseño ascendente (bottom-up)

El enfoque ascendente inicia con los componentes individuales más pequeños y progresa hacia niveles superiores de abstracción:

1. Se identifican y desarrollan los componentes básicos reutilizables.
2. Estos componentes se combinan para formar componentes de nivel medio.
3. La integración continúa hasta formar el sistema completo.

Este enfoque resulta especialmente apropiado cuando:

- Los componentes básicos son claramente identificables
- Se desea priorizar la reutilización
- La experimentación y el aprendizaje son importantes
- El problema completo no está completamente definido

### Tipos de jerarquías

En el diseño de software, pueden identificarse varios tipos de jerarquías:

#### De composición

Representa relaciones "tiene-un" o "parte-de".

   ```java
   public class Coche {
       private Motor motor;
       private List<Rueda> ruedas;
       private Chasis chasis;
       // ...
   }
   ```

#### De clasificación

Representa relaciones "es-un" a través de herencia.

   ```java
   public abstract class Vehículo {
       // Atributos y métodos comunes
   }
   
   public class Coche extends Vehículo {
       // Especializaciones
   }
   
   public class Motocicleta extends Vehículo {
       // Especializaciones
   }
   ```

#### De dependencias

Representa relaciones "usa" entre componentes.

   ```java
   public class ServicioCliente {
       private RepositorioCliente repositorio;
       private ServicioNotificacion notificacion;
       
       // Métodos que usan estos servicios
   }
   ```

#### De módulos

Organización lógica de código en paquetes, namespaces o módulos.

   ```
   com.empresa.producto
   ├── dominio
   │   ├── modelo
   │   └── servicio
   ├── infraestructura
   │   ├── persistencia
   │   └── comunicación
   └── interfaz
       ├── web
       └── api
   ```

### Propiedades de jerarquías bien diseñadas

Una jerarquía efectiva debe cumplir con ciertas propiedades:

1. **Acíclica**: Las dependencias entre componentes no deben formar ciclos.
1. **Direccional**: Las dependencias deben fluir principalmente en una dirección consistente.
1. **Estable**: Los niveles inferiores deben ser más estables que los superiores.
1. **Abstracción gradual**: Cada nivel debe proporcionar una abstracción coherente sobre los niveles inferiores.
1. **Encapsulación**: Los detalles internos de cada nivel deben estar ocultos a los niveles superiores.

## ¿Para qué?

La aplicación consistente del principio de jerarquización produce sistemas con las siguientes características positivas:

- **Comprensibilidad cognitiva**: Los desarrolladores pueden entender el sistema por niveles, sin necesidad de comprender todos los detalles simultáneamente.
- **Localización de cambios**: Las modificaciones tienden a estar contenidas dentro de componentes específicos sin propagarse por todo el sistema.
- **Independencia de desarrollo**: Diferentes equipos pueden trabajar en diferentes componentes con mínima coordinación directa.
- **Facilidad de navegación**: La estructura jerárquica proporciona un "mapa" para localizar rápidamente el código relevante.
- **Evolución independiente**: Los diferentes componentes pueden evolucionar a distintos ritmos según sus necesidades específicas.

<div align=center>

| Jerarquización Efectiva |||| Jerarquización Deficiente |
|-|-:|:-:|:-|-|
|Comprensible por niveles       |**Fluidez**|     *vs*|**Viscosidad**  | Requiere comprensión total |
|Cambios localizados            |**Flexibilidad**|*vs*|**Rigidez**| Cambios de amplio impacto |
|Detección temprana de problemas|**Robustez**|*vs*|**Fragilidad**| Fallos propagados a todo el sistema |
|Componentes reaprovechables    |**Reusabilidad**|    *vs*|**Inmovilidad**  | Componentes atados a su contexto |

</div>

La metáfora del "mapa de carreteras" resulta útil para comprender este principio:

> "Un sistema bien jerarquizado es como un mapa de carreteras con niveles de zoom: a nivel nacional se ven las autopistas principales, al acercar el zoom aparecen las carreteras secundarias, y finalmente las calles locales. En cualquier momento puedes navegar con confianza sabiendo que existe una estructura coherente, sin necesidad de ver todos los caminos a la vez."

## ¿Cómo?

Para aplicar efectivamente el principio de jerarquización en el diseño de software, se pueden seguir estas estrategias prácticas:

### Identificar síntomas de problemas jerárquicos

- **Dependencias cíclicas**: Componentes que dependen mutuamente entre sí.
- **Estructura plana**: Todos los componentes al mismo nivel sin abstracciones intermedias.
- **Acoplamiento distante**: Componentes que dependen de otros muy alejados en la estructura lógica.
- **Visibilidad excesiva**: Componentes internos expuestos innecesariamente a capas superiores.

### Enfoques estructurados de diseño

#### Estrategia híbrida (top-down + bottom-up)

En la práctica, muchos proyectos exitosos combinan ambos enfoques:

1. Establecer una **visión arquitectónica global** (top-down) que defina los principales subsistemas y sus interacciones.
2. Desarrollar **componentes fundamentales reutilizables** (bottom-up) que provean funcionalidades básicas comunes.
3. Proceder con un enfoque **iterativo e incremental** que refine tanto la arquitectura global como los componentes individuales.

```java
// Definición top-down de interfaces
public interface RepositorioCliente {
    Cliente buscarPorId(String id);
    void guardar(Cliente cliente);
    // ...
}

// Implementación bottom-up de componentes específicos
public class RepositorioClienteJPA implements RepositorioCliente {
    @Override
    public Cliente buscarPorId(String id) {
        // Implementación específica
    }
    
    @Override
    public void guardar(Cliente cliente) {
        // Implementación específica
    }
    // ...
}
```

#### Arquitectura por capas

Organizar el sistema en capas horizontales con responsabilidades bien definidas:

```
┌─────────────────────────┐
│     Interfaz Usuario    │
├─────────────────────────┤
│   Lógica de Aplicación  │
├─────────────────────────┤
│    Lógica de Dominio    │
├─────────────────────────┤
│     Infraestructura     │
└─────────────────────────┘
```

Cada capa:

- Proporciona servicios a la capa superior
- Puede utilizar servicios de la capa inferior
- Idealmente, no conoce las capas por encima de ella

```java
// Capa de dominio
public class Cliente {
    private String id;
    private String nombre;
    // Lógica de dominio
}

// Capa de aplicación
public class ServicioCliente {
    private RepositorioCliente repositorio;
    
    public void registrarCliente(DatosCliente datos) {
        Cliente cliente = new Cliente(datos);
        // Lógica de aplicación
        repositorio.guardar(cliente);
    }
}

// Capa de interfaz
public class ControladorCliente {
    private ServicioCliente servicio;
    
    public ResponseEntity<String> registrar(RequestCliente request) {
        // Adaptación de datos de interfaz
        servicio.registrarCliente(request.toDatosCliente());
        return ResponseEntity.ok("Cliente registrado");
    }
}
```

### Principios de diseño

#### Principio de dependencias acíclicas (ADP)

No permitir ciclos en el grafo de dependencias entre componentes.

```
// Correcto: Dependencias acíclicas
A → B → C → D

// Incorrecto: Ciclo de dependencias
A → B → C → A
```

Para romper ciclos de dependencias, se pueden utilizar:

- Interfaces (Inversión de Dependencias)
- Patrones de diseño como Observer o Mediator
- Reestructuración de componentes

#### Principio de dependencias estables (SDP)

Los componentes más estables deben estar en la parte inferior de la jerarquía, y los más volátiles en la parte superior.

```
    Componentes Volátiles
           ↓
    Componentes de Aplicación
           ↓
    Componentes Estables (Librerías)
```

#### Principio de Abstracciones Estables (SAP)

Los componentes estables deben ser más abstractos para permitir extensibilidad sin modificación.

### Herramientas de visualización y análisis

- **Diagramas de componentes UML**: Para visualizar la estructura jerárquica.
- **Herramientas de análisis de dependencias**: Como JDepend, Structure101, o SonarQube.
- **Visualizadores de arquitectura**: Como CodeCity o CodeFlower.

### Técnicas de implementación

#### Empaquetado efectivo

Organizar el código en una estructura de paquetes/módulos que refleje la jerarquía conceptual:

```
com.empresa.producto
├── dominio
│   ├── cliente
│   │   ├── Cliente.java
│   │   ├── ClienteId.java
│   │   └── DireccionCliente.java
│   └── pedido
│       ├── Pedido.java
│       ├── LineaPedido.java
│       └── EstadoPedido.java
├── aplicacion
│   ├── cliente
│   │   ├── ServicioCliente.java
│   │   └── dto
│   └── pedido
│       └── ServicioPedido.java
└── infraestructura
    ├── persistencia
    │   ├── jpa
    │   └── mongodb
    └── api
        └── rest
```

#### Control de visibilidad

Utilizar modificadores de acceso para reforzar la jerarquía:

```java
// Paquete interno oculto a otros módulos
package com.empresa.producto.dominio.interno;

// Clase pública accesible desde otros módulos
public class Cliente {
    // Constructor interno - solo accesible dentro del paquete
    Cliente(String nombre) {
        this.nombre = nombre;
    }
    
    // Método protegido - accesible solo a subclases
    protected void validarEstado() {
        // ...
    }
    
    // Método privado - accesible solo dentro de la clase
    private void logCambio() {
        // ...
    }
}
```

#### Inversión de Dependencias

Usar interfaces para invertir dependencias entre capas:

```java
// En la capa de dominio:
public interface RepositorioCliente {
    Cliente obtenerPorId(String id);
    void guardar(Cliente cliente);
}

// En la capa de infraestructura:
public class RepositorioClienteMongoDB implements RepositorioCliente {
    // Implementación específica que depende de MongoDB
}
```
