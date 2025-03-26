# Cohesión

## ¿Por qué?

En el desarrollo de software, frecuentemente se generan sistemas con componentes que asumen múltiples responsabilidades desconectadas entre sí. Esta situación produce código que resulta difícil de entender, modificar, probar y reutilizar.

Cuando un módulo, clase o método abarca demasiadas responsabilidades poco relacionadas entre sí, se consume lo que podría denominarse como "canal cognitivo" —la capacidad mental necesaria para comprenderlo. Este fenómeno genera sistemas que presentan las siguientes características negativas:

- **Viscosidad**: No se logra entender con facilidad debido a la multiplicidad de propósitos entremezclados.
- **Rigidez**: No se puede modificar con facilidad sin provocar efectos secundarios no deseados.
- **Fragilidad**: No se puede probar con facilidad y resulta propenso a fallar cuando se modifica.
- **Inmovilidad**: No se puede reutilizar con facilidad en otros contextos.

Las manifestaciones típicas de esta problemática incluyen clases excesivamente grandes con métodos extensos, numerosos atributos, y múltiples responsabilidades, creando lo que coloquialmente se conoce como "clases Dios" o "métodos monstruo".

## ¿Qué?

La cohesión es un principio fundamental del diseño modular que mide la fortaleza con la que se relacionan las responsabilidades asignadas a un elemento del software.

> "La cohesión, o más específicamente, la **cohesión funcional**, es una **medida de cómo de fuerte están relacionadas y enfocadas las responsabilidades** que son de un elemento. Estos elementos incluyen **sistemas, paquetes, clases y métodos**."
>
> — Craig Larman, UML y Patrones

Este concepto fue formulado inicialmente por Larry Constantine en la década de 1960, y refinado posteriormente en colaboración con Ed Yourdon y Wayne Stevens. La cohesión constituye uno de los tres pilares fundamentales del diseño modular, junto con el bajo acoplamiento y el tamaño adecuado.

### Niveles de cohesión

Se pueden identificar los siguientes niveles de cohesión, ordenados de mejor a peor:

1. **Cohesión funcional** (la más alta): El módulo realiza una única tarea bien definida.

   ```java
   public double calcularAreaCirculo(double radio) {
       return Math.PI * radio * radio;
   }
   ```

2. **Cohesión secuencial**: La salida de una parte del módulo sirve como entrada para otra.

   ```java
   public String procesarTexto(String texto) {
       String textoNormalizado = texto.toLowerCase().trim();
       return eliminarCaracteresEspeciales(textoNormalizado);
   }
   ```

3. **Cohesión comunicacional**: Las partes del módulo operan sobre los mismos datos.

   ```java
   public class ProcesadorPedido {
       public void procesarPedido(Pedido pedido) {
           validarStock(pedido);
           calcularTotal(pedido);
           aplicarDescuentos(pedido);
       }
   }
   ```

4. **Cohesión procedimental**: Las partes del módulo se ejecutan en secuencia pero operan sobre datos diferentes.

   ```java
   public class ProcesamientoPedido {
       public void procesarNuevoPedido(int idPedido, Cliente cliente, List<Producto> productos) {
           verificarDisponibilidadProductos(productos);
           actualizarHistorialCliente(cliente);
           generarFactura(idPedido, productos);
           registrarEstadisticasVenta(productos);
           enviarCorreoConfirmacion(cliente.getEmail(), idPedido);
       }
   }
   ```

   En este ejemplo, el método `procesarNuevoPedido` tiene cohesión procedimental porque sus componentes se ejecutan en una secuencia específica, pero cada paso trabaja con diferentes datos y representa responsabilidades distintas que podrían pertenecer a subsistemas diferentes (inventario, gestión de clientes, facturación, estadísticas y comunicaciones).

5. **Cohesión temporal**: Actividades relacionadas por tiempo de ejecución pero no funcionalmente.

   ```java
   public void inicializarSistema() {
       conectarBaseDatos();
       cargarConfiguracion();
       iniciarInterfazUsuario();
   }
   ```

6. **Cohesión lógica**: El módulo realiza funciones lógicamente relacionadas pero funcionalmente diferentes.

   ```java
   public void procesarEntrada(String tipo, Object datos) {
       switch(tipo) {
           case "teclado": procesarTeclado(datos); break;
           case "mouse": procesarMouse(datos); break;
           case "voz": procesarVoz(datos); break;
       }
   }
   ```

7. **Cohesión coincidental** (la más baja): Las partes del módulo no tienen relación entre sí.

   ```java
   public class Utilidades {
       public static void enviarEmail() { /*...*/ }
       public static double calcularImpuesto() { /*...*/ }
       public static void optimizarImagen() { /*...*/ }
   }
   ```

### Alta vs. baja cohesión

En el diseño de software, se distinguen dos situaciones contrapuestas:

<div align=center>

|Alta cohesión|Baja cohesión|
|-|-|
|Se asignan responsabilidades muy relacionadas entre sí|Se asignan responsabilidades escasamente relacionadas|
|No se realiza una cantidad excesiva de trabajo|Se realiza una cantidad desproporcionada de trabajo|
|Se delega adecuadamente a otros objetos|Se asumen responsabilidades que deberían delegarse|
|Se implementa un número relativamente pequeño de métodos con funcionalidad relacionada|Se mantiene una cohesión moderada, con responsabilidades únicas pero en áreas diferentes|
|Se colabora con otros objetos para distribuir el esfuerzo||

</div>

### Principio de responsabilidad única (SRP)

El Principio de Responsabilidad Única, uno de los principios SOLID definido por Robert C. Martin, está directamente relacionado con la cohesión:

> "Una clase debería tener un único motivo de cambio."

Este principio redefine ligeramente el concepto tradicional de cohesión, enfocándolo en el origen del cambio. Si existen múltiples razones para modificar una clase, entonces esa clase tiene más de una responsabilidad, lo que implica baja cohesión.

### Compromisos y excepciones

Se pueden identificar ciertos contextos específicos donde podría aceptarse una menor cohesión:

1. **Clases de utilidad**: Agrupaciones de funciones estáticas de uso frecuente, como las clases `Collections` o `Math` en Java.
1. **Servicios distribuidos**: Objetos remotos donde el costo de la comunicación justifica agrupar más operaciones en un solo componente.
1. **Frameworks legacy**: Sistemas heredados donde la refactorización representaría un riesgo mayor que mantener la baja cohesión.

Sin embargo, estos casos deben considerarse excepciones, no la regla.

## ¿Para qué?

La implementación de componentes con alta cohesión produce sistemas con las siguientes características positivas:

- **Fluidez**: Se puede entender con facilidad debido a la claridad de propósito de cada componente
- **Flexibilidad**: Se puede modificar con facilidad al minimizar las dependencias entre componentes
- **Reusabilidad**: Se puede reutilizar con facilidad al tener responsabilidades bien definidas
- **Robustez**: Se reduce la fragilidad ante cambios al limitar los efectos secundarios

Cuando se compara con un diseño de baja cohesión, las ventajas resultan evidentes:

<div align=center>

| Alta Cohesión |||| Baja Cohesión |
|-|-:|:-:|:-|-|
|Fácil de entender                  |**Fluidez**|     *vs*|**Viscosidad**  | Difícil de comprender |
|Sencilla de modificar              |**Flexibilidad**|*vs*|**Rigidez**| Complicada de modificar |
|Preparada para reutilizar          |**Reusabilidad**|*vs*|**Inmovilidad**| Imposible de reutilizar |
|Resistente a efectos secundarios   |**Robustez**|    *vs*|**Fragilidad**| Constantemente afectada por cambios |

</div>

Como analogía práctica para recordar la importancia de la cohesión, puede utilizarse la del mosaico:

> "Es difícil hacer cualquier mosaico con fichas grandes de varios colores (baja cohesión) y formas irregulares (alto acoplamiento). Es fácil hacer cualquier mosaico con fichas pequeñas de un solo color (alta cohesión) y formas cuadradas (bajo acoplamiento)." ***L.Fernández***

## ¿Cómo?

Para aplicar correctamente el principio de cohesión en el diseño de software, deben seguirse estas estrategias prácticas:

### Identificar "code smells" que señalan problemas de cohesión

- **[Clases alternativas con distintas interfaces](sc.acdi.md)**: Clases que realizan funcionalidades similares pero con APIs diferentes, creando inconsistencia conceptual.
- **[Envidia de características](sc.fe.md)**: Cuando un método parece más interesado en los datos o comportamientos de otra clase que en los propios.
- **[Clase de datos](sc.dc.md)**: Clases con solo atributos y getters/setters sin comportamiento real que encapsule reglas de negocio.
- **[Cambios divergentes](sc.dch.md)**: Cuando una clase cambia por múltiples razones no relacionadas, indicando responsabilidades mezcladas.
- **[Cirugía con escopeta](sc.ss.md)**: Cuando un solo cambio conceptual requiere modificar muchas clases distintas, señalando responsabilidades fragmentadas.
- **[Grupo de datos](sc.dc.md)**: Los mismos grupos de campos aparecen juntos en múltiples lugares, sugiriendo que deberían ser su propia clase.
- **[Obsesión por tipos primitivos](sc.po.md)**: Uso excesivo de tipos primitivos en lugar de pequeñas clases de dominio con alta cohesión.
- **[Clases perezosas](sc.lc.md)**: Clases que hacen demasiado poco para justificar su existencia, fragmentando conceptos que deberían estar juntos.
- **[Métodos largos](sc.lm.md)**: Métodos que realizan múltiples operaciones, violando la cohesión a nivel de método.
- **[Clases grandes](sc.lcl.md)**: Clases con demasiadas responsabilidades que abarcan conceptos distintos.

### Técnicas de diseño & refactorización

- **Extraer método**: Debe separarse un fragmento de código en un método con un nombre representativo.

   ```java
   // Antes
   public void procesarPedido() {
       // Verificar disponibilidad
       for (Item item : items) {
           if (item.getStock() < item.getCantidad()) {
               throw new StockInsuficienteException();
           }
       }
       
       // Calcular total
       double total = 0;
       for (Item item : items) {
           total += item.getPrecio() * item.getCantidad();
       }
       
       // etc...
   }
   
   // Después
   public void procesarPedido() {
       verificarDisponibilidad();
       double total = calcularTotal();
       // etc...
   }
   
   private void verificarDisponibilidad() {
       for (Item item : items) {
           if (item.getStock() < item.getCantidad()) {
               throw new StockInsuficienteException();
           }
       }
   }
   
   private double calcularTotal() {
       double total = 0;
       for (Item item : items) {
           total += item.getPrecio() * item.getCantidad();
       }
       return total;
   }
   ```

- **Extraer clase**: Debe crearse una nueva clase para responsabilidades específicas.

   ```java
   // Antes
   public class Pedido {
       private List<Item> items;
       private Cliente cliente;
       private double total;
       
       // Lógica de pedido
       
       // Métodos para envío
       public void calcularCostoEnvio() { /*...*/ }
       public void seleccionarTransportista() { /*...*/ }
       public void generarEtiquetaEnvio() { /*...*/ }
   }
   
   // Después
   public class Pedido {
       private List<Item> items;
       private Cliente cliente;
       private double total;
       private Envio envio;
       
       // Lógica de pedido
   }
   
   public class Envio {
       private Pedido pedido;
       private Transportista transportista;
       
       public void calcularCosto() { /*...*/ }
       public void seleccionarTransportista() { /*...*/ }
       public void generarEtiqueta() { /*...*/ }
   }
   ```

- **Mover método/campo**: Debe reubicarse funcionalidad en la clase apropiada.

   ```java
   // Antes
   public class Cliente {
       // ...
       public double calcularDescuentoPedido(Pedido pedido) {
           if (esClienteVIP()) {
               return pedido.getTotal() * 0.1;
           }
           return 0;
       }
   }
   
   // Después
   public class Cliente {
       // ...
       public boolean esClienteVIP() {
           return // ...
       }
   }
   
   public class Pedido {
       // ...
       public double calcularDescuento(Cliente cliente) {
           if (cliente.esClienteVIP()) {
               return getTotal() * 0.1;
           }
           return 0;
       }
   }
   ```

- **Introducir objeto-parámetro**: Deben agruparse parámetros relacionados en una clase.

   ```java
   // Antes
   public void registrarEnvio(String calle, String ciudad, 
                           String provincia, String codigoPostal,
                           String pais) {
       // ...
   }
   
   // Después
   public void registrarEnvio(Direccion direccion) {
       // ...
   }
   
   public class Direccion {
       private String calle;
       private String ciudad;
       private String provincia;
       private String codigoPostal;
       private String pais;
       
       // Constructor, getters, etc.
   }
   ```

### Métricas para evaluar la cohesión

Pueden emplearse métricas formales para medir la cohesión:

- **LCOM (Lack of Cohesion in Methods)**: Mide la disparidad entre métodos de una clase. Un valor alto indica baja cohesión.
- **LCOM4**: Una versión mejorada de LCOM que considera las relaciones indirectas entre métodos.
- **TCC (Tight Class Cohesion)**: Mide el porcentaje de pares de métodos visibles públicamente que comparten al menos un atributo.
- **LCC (Loose Class Cohesion)**: Similar a TCC pero considera conexiones indirectas.

### Herramientas de análisis estático de código

Se recomienda implementar herramientas como SonarQube, JArchitect o NDepend que pueden detectar automáticamente problemas de cohesión y ofrecer recomendaciones para mejorar la calidad del código.
