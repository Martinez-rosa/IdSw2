# Interfaces fluidas

## ¿Por qué?

En el desarrollo de software tradicional, las operaciones que requieren múltiples configuraciones suelen implementarse a través de una serie de llamadas a métodos individuales. Este enfoque presenta varios inconvenientes:

- Es necesario repetir constantemente la referencia al objeto
- Se requiere mayor esfuerzo para seguir la secuencia lógica de las operaciones
- Resulta difícil visualizar el flujo completo de la configuración
- Se genera código más extenso y menos legible

Por ejemplo, un código tradicional para configurar un objeto podría verse así:

```java
Formulario formulario = new Formulario();
formulario.setTitulo("Registro de Usuario");
formulario.agregarCampo("nombre", TipoCampo.TEXTO);
formulario.agregarCampo("email", TipoCampo.EMAIL);
formulario.agregarCampo("contraseña", TipoCampo.PASSWORD);
formulario.setAccionEnvio("/usuarios/crear");
formulario.setMetodo("POST");
formulario.validar();
```

## ¿Qué?

Las interfaces fluidas (Fluent Interfaces) representan un patrón de diseño que permite encadenar múltiples llamadas a métodos en una única expresión. Este patrón se basa en el principio de que cada método, en lugar de devolver void, retorna una referencia al objeto actual (this), lo que permite continuar la cadena de llamadas.

El concepto fue introducido por Martin Fowler y Eric Evans, quienes buscaban crear APIs más expresivas y cercanas al lenguaje natural.

La clave de una interfaz fluida reside en tres principios:

- Métodos que retornan el objeto actual (this)
- Nombres de métodos que reflejan un lenguaje de dominio específico
- Una estructura que guía al programador a través de operaciones relacionadas

## ¿Para qué?

La implementación de interfaces fluidas proporciona numerosas ventajas:

- Se mejora la legibilidad del código al aproximarlo al lenguaje natural
- Se reduce la verbosidad y repetición de referencias a objetos
- Se guía al desarrollador a través de una secuencia lógica de operaciones
- Se facilita la identificación de errores en tiempo de compilación
- Se crean APIs más intuitivas y autodocumentadas
- Se incrementa la productividad al reducir el código necesario para tareas comunes

## ¿Cómo?

<div align=center>

|[Ejemplo](Ejemplo.java)
|-
|[Notificacion](Notificacion.java)
|[**NotificacionBuilder**](NotificacionBuilder.java)
|[ServicioNotificaciones](ServicioNotificaciones.java)
|[ResultadoEnvio](ResultadoEnvio.java)

</div>

### Conceptos clave del encadenamiento de mensajes

<div align=center>

|Retorno consistente del objeto actual|Métodos terminales vs. intermedios|Orden semántico|
|-|-|-|
|Cada método de configuración debe devolver `this` (referencia al objeto actual) para permitir el encadenamiento.|Los métodos como `construir()` o `enviar()` son terminales (no devuelven `this` sino otro tipo de objeto), mientras que los métodos como `conTitulo()` o `urgente()` son intermedios (permiten continuar el encadenamiento).|Los nombres de los métodos deben formar frases coherentes cuando se encadenan, aproximándose al lenguaje natural.



|Inmutabilidad opcional|Validación diferida|
|-|-|
|Algunas implementaciones avanzadas de interfaces fluidas crean objetos inmutables, donde cada método devuelve una nueva instancia en lugar de modificar la existente.|El objeto no valida su estado hasta que se llama a un método terminal como `construir()` o `enviar()`.

</div>