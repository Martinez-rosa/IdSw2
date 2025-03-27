# Tamaño

## ¿Por qué?

El desarrollo de software frecuentemente se enfrenta a problemas derivados del dimensionamiento inadecuado de sus componentes. Cuando un elemento del sistema (paquete, clase, método o función) crece desproporcionadamente, se genera una estructura que resulta difícil de comprender, modificar y mantener.

Esta situación produce sistemas que presentan las siguientes características negativas:

- **Complejidad cognitiva excesiva**: Los componentes demasiado grandes requieren un esfuerzo mental significativo para ser comprendidos. El cerebro humano tiene una capacidad limitada para procesar información simultáneamente, conocida como carga cognitiva. Cuando una unidad de código supera cierto umbral de tamaño, se vuelve imposible mantenerla completa en la memoria de trabajo.
- **Sobrecarga de responsabilidades**: Los elementos grandes tienden a acumular múltiples responsabilidades no relacionadas, violando el Principio de Responsabilidad Única y disminuyendo la cohesión.
- **Dificultad de prueba**: Las unidades de código extensas resultan difíciles de probar exhaustivamente debido a su elevado número de rutas de ejecución potenciales y estados posibles.
- **Barreras a la colaboración**: Cuando los componentes son demasiado grandes, se complica el desarrollo en paralelo, ya que múltiples desarrolladores necesitarían modificar las mismas áreas de código simultáneamente.
- **Propensión a errores**: Las estructuras de gran tamaño suelen presentar mayor densidad de defectos, pues resulta más difícil razonar sobre su funcionamiento completo y anticipar interacciones problemáticas.

Las manifestaciones típicas de este problema incluyen "*clases dios*" con cientos de métodos, funciones con decenas de parámetros, métodos con centenares de líneas, o paquetes con decenas de clases sin una organización lógica.

## ¿Qué?

El principio de tamaño es una métrica fundamental del diseño modular que establece límites dimensionales óptimos para los distintos componentes de un sistema de software, con el objetivo de mantener su comprensibilidad y mantenibilidad.

Este principio fue sintetizado a partir de múltiples investigaciones sobre psicología cognitiva y experiencia práctica en desarrollo de software, especialmente los trabajos de George Miller sobre "El número mágico siete, más o menos dos", los estudios de Edsger Dijkstra sobre modularización, y las prácticas de desarrollo ágil promovidas por Robert C. Martin y Kent Beck.

### Restricciones de tamaño por nivel

El diseño modular establece restricciones de tamaño para cada nivel de la jerarquía de componentes de software:

<div align=center>

|Elemento|Cota|Cantidad|Elemento|
|-|-|-|-|
|**Paquete**|Máximo|[12 .. 20]|**Clases**|
|**Clase**|Media|3|**Atributos**|
|**Clase**|Máximo|5|**Atributos**|
|**Clase**|Máximo|[20 .. 25]|**Métodos**|
|**Clase**|Máximo|[200 .. 500]|**Líneas**|
|**Método**|Media|1,2| **Parámetros**|
|**Método**|Máximo|[2 .. 3]|**Parámetros**|
|**Método**|Máximo|[10 .. 15-25] | **Líneas de código** |
|**Método**|Máximo|3| **Sentencias anidadas** |
|**Método**|Máximo|[10 .. 15] | **Complejidad ciclomática** |
|**Línea**|Máximo|80\|120| **Caracteres** |

</div>

### Fundamentos teóricos

#### Regla 7±2

George Miller estableció en 1956 que la capacidad de la memoria de trabajo humana está limitada aproximadamente a 7±2 elementos de información simultáneos. Esto implica que:

- Un método no debería tener más de 5-9 variables locales
- Una clase no debería exponer más de 5-9 métodos públicos
- Una función no debería tener más de 5-9 puntos de decisión

#### Ley de proximidad

Esta ley de la psicología Gestalt indica que elementos cercanos tienden a percibirse como relacionados. Aplicado al código:

- El código relacionado debe mantenerse cercano
- Si dos fragmentos están separados por más de una pantalla, su relación se vuelve menos evidente
- Implica métodos cortos donde toda su lógica sea visible sin necesidad de desplazamiento

#### Complejidad ciclomática

Medida desarrollada por Thomas McCabe que cuantifica el número de caminos independientes a través de un programa, relacionándose directamente con la complejidad de prueba:

- `CC = Número de condiciones + Número de retornos o salidas`
- Valores recomendados: 1-10 (simple), 11-20 (complejo), >20 (muy complejo)
- Un valor superior a 10-15 indica un método que probablemente debería ser refactorizado

## ¿Para qué?

La aplicación deliberada del principio de tamaño en el diseño de software produce sistemas con las siguientes características positivas:

- **Comprensibilidad mejorada**: Los componentes de tamaño adecuado pueden entenderse completamente en un solo vistazo, facilitando su análisis y comprensión.
- **Mantenibilidad incrementada**: Las unidades pequeñas pueden modificarse con mayor confianza y menor riesgo de introducir efectos secundarios inesperados.
- **Mayor testabilidad**: Los componentes pequeños tienen menos rutas de ejecución y estados posibles, lo que simplifica su prueba exhaustiva.
- **Reutilización facilitada**: Los elementos de tamaño reducido tienden a tener responsabilidades más específicas y menos dependencias, haciéndolos más adecuados para su reutilización.
- **Colaboración optimizada**: Unidades más pequeñas permiten que múltiples desarrolladores trabajen en paralelo con menor probabilidad de conflictos.

<div align=center>

| Tamaño Adecuado |||| Tamaño Excesivo |
|-|-:|:-:|:-|-|
|Comprensible de un vistazo        |**Fluidez**|     *vs*|**Viscosidad**  | Requiere análisis fragmentado |
|Modificable con confianza         |**Flexibilidad**|*vs*|**Rigidez**| Requiere cambios cuidadosos |
|Específico y enfocado             |**Reusabilidad**|    *vs*|**Inmovilidad**  | Demasiado específico para reutilizar |
|Fácilmente testeable              |**Robustez**|*vs*|**Fragilidad**| Imposible de probar exhaustivamente |

</div>

La metáfora del "bonsái de código" resulta útil para comprender este principio:

> "Al igual que un maestro bonsái poda cuidadosamente las ramas para mantener el árbol saludable y armonioso, un buen desarrollador controla constantemente el tamaño de sus componentes mediante refactorización, asegurando que cada elemento mantenga un propósito claro y un tamaño óptimo."

## ¿Cómo?

Para aplicar correctamente el principio de tamaño en el diseño de software, deben seguirse estas estrategias prácticas:

### Identificar "code smells" relacionados con tamaño excesivo

- **[Método largo](sc.lm.md)**: Métodos con demasiadas líneas de código.
- **[Lista de parámetros larga](sc.lpl.md)**: Métodos con demasiados parámetros.
- **[Clase grande](sc.lc.md)**: Clases con demasiados atributos y/o métodos.
- **[Campos temporales](sc.tf.md)**: Campos (atributos) cuya utilidad no es constante a lo largo del ciclo de vida de los objetos.
- **[Complejidad ciclomática alta](https://es.wikipedia.org/wiki/Complejidad_ciclom%C3%A1tica)** *ext*: Muchos caminos de ejecución en un método.
- **Comentarios extensos**: Necesidad de explicaciones largas suele indicar complejidad excesiva.
- **Anidación profunda**: Estructuras condicionales o bucles excesivamente anidados.
- **Declaraciones switch grandes**: Sentencias switch con muchos casos.

### Técnicas de refactorización

- **Extraer Método**: Descomponer métodos grandes en métodos más pequeños y enfocados.

```java
// Antes: Método largo con múltiples responsabilidades
public void procesarPedido(Pedido pedido) {
    // Verificar stock (20 líneas)...
    
    // Calcular precio (30 líneas)...
    
    // Aplicar descuentos (25 líneas)...
    
    // Registrar en sistema (40 líneas)...
    
    // Enviar confirmación (15 líneas)...
}

// Después: Métodos pequeños y enfocados
public void procesarPedido(Pedido pedido) {
    verificarDisponibilidadStock(pedido);
    calcularPrecioFinal(pedido);
    aplicarDescuentos(pedido);
    registrarPedido(pedido);
    enviarConfirmacion(pedido);
}

private void verificarDisponibilidadStock(Pedido pedido) {
    // 20 líneas...
}

private void calcularPrecioFinal(Pedido pedido) {
    // 30 líneas...
}

// Métodos adicionales...
```

- **Introducir objeto-parámetro**: Agrupar parámetros relacionados en objetos.

```java
// Antes: Método con muchos parámetros
public void registrarCliente(String nombre, String apellido, String calle, 
                          String ciudad, String estado, String codigoPostal,
                          String telefono, String email) {
    // Implementación...
}

// Después: Parámetros agrupados en objetos
public void registrarCliente(DatosPersonales datos, Direccion direccion, 
                          InformacionContacto contacto) {
    // Implementación...
}

// Clases de parámetros
public class DatosPersonales {
    private String nombre;
    private String apellido;
    // Getters, setters...
}

public class Direccion {
    private String calle;
    private String ciudad;
    private String estado;
    private String codigoPostal;
    // Getters, setters...
}

public class InformacionContacto {
    private String telefono;
    private String email;
    // Getters, setters...
}
```

- **Extraer clase**: Dividir clases grandes en clases más pequeñas y cohesivas.

```java
// Antes: Clase grande con múltiples responsabilidades
public class GestorPedidos {
    // Atributos para gestionar pedidos
    // Atributos para facturación
    // Atributos para envío
    
    // Métodos para gestionar pedidos
    // Métodos para facturación
    // Métodos para envío
}

// Después: Clases pequeñas y especializadas
public class GestorPedidos {
    private ServicioFacturacion facturacion;
    private ServicioEnvio envio;
    
    // Métodos para gestionar pedidos únicamente
}

public class ServicioFacturacion {
    // Atributos y métodos específicos para facturación
}

public class ServicioEnvio {
    // Atributos y métodos específicos para envío
}
```

- **Reemplazar condicional con polimorfismo**: Eliminar condicionales complejos usando herencia.

```java
// Antes: Método con condicional complejo
public double calcularSalario(Empleado empleado) {
    switch (empleado.getTipo()) {
        case TIEMPO_COMPLETO:
            return empleado.getSalarioBase();
        case TIEMPO_PARCIAL:
            return empleado.getHorasTrabajadas() * empleado.getTarifaHoraria();
        case COMISION:
            return empleado.getSalarioBase() + empleado.getVentas() * empleado.getPorcentajeComision();
        default:
            throw new IllegalArgumentException("Tipo de empleado desconocido");
    }
}

// Después: Jerarquía polimórfica
public abstract class Empleado {
    public abstract double calcularSalario();
}

public class EmpleadoTiempoCompleto extends Empleado {
    private double salarioBase;
    
    @Override
    public double calcularSalario() {
        return salarioBase;
    }
}

public class EmpleadoTiempoParcial extends Empleado {
    private int horasTrabajadas;
    private double tarifaHoraria;
    
    @Override
    public double calcularSalario() {
        return horasTrabajadas * tarifaHoraria;
    }
}

public class EmpleadoComision extends Empleado {
    private double salarioBase;
    private double ventas;
    private double porcentajeComision;
    
    @Override
    public double calcularSalario() {
        return salarioBase + ventas * porcentajeComision;
    }
}
```

- **Simplificar expresiones condicionales**: Extraer condiciones complejas en métodos con nombres descriptivos.

```java
// Antes: Condición compleja
if (usuario.getEdad() >= 18 && usuario.getTipoDocumento() != null && 
    (usuario.getPais().equals("España") || usuario.getTienePermiso()) && 
    !usuario.getListaNegra()) {
    // Permitir acceso
}

// Después: Método con nombre descriptivo
if (usuarioCumpleRequisitosAcceso(usuario)) {
    // Permitir acceso
}

private boolean usuarioCumpleRequisitosAcceso(Usuario usuario) {
    return usuario.getEdad() >= 18 && 
           usuario.getTipoDocumento() != null && 
           (usuario.getPais().equals("España") || usuario.getTienePermiso()) && 
           !usuario.getListaNegra();
}
```

### Heurísticas de diseño

- **Regla de las 7 líneas**: Un método idealmente debería tener alrededor de 7 líneas.
- **Una clase, una responsabilidad**: Aplicar el principio de responsabilidad única.
- **Nivel único de abstracción**: Mantener un nivel consistente de abstracción dentro de cada método.
- **Métodos atómicos**: Cada método debe hacer exactamente una cosa.

### Herramientas de análisis de código

Se recomienda implementar herramientas de análisis estático que pueden detectar automáticamente componentes sobredimensionados:

- **SonarQube**: Detecta violaciones en longitud de métodos, clases, complejidad ciclomática.
- **CheckStyle**: Implementa verificaciones de tamaño configurables.
- **PMD**: Identifica métodos largos, clases grandes y complejidad excesiva.
- **NDepend/JArchitect**: Proporciona métricas avanzadas y visualizaciones.

```xml
<!-- Ejemplo de regla CheckStyle para limitar tamaño de métodos -->
<module name="MethodLength">
    <property name="tokens" value="METHOD_DEF"/>
    <property name="max" value="25"/>
    <property name="countEmpty" value="false"/>
</module>
```

### Establecer convenciones de equipo

- Definir estándares de tamaño específicos para el proyecto
- Incluir límites de tamaño en las revisiones de código
- Implementar acciones automáticas para componentes que exceden umbrales críticos
- Programar refactorizaciones periódicas para componentes que crecen con el tiempo
