# Middle Man

Ocurre cuando una clase no realiza suficiente trabajo por sí misma y solo delega a otras clases. Este smell code se presenta cuando una clase actúa principalmente como intermediario, limitándose a pasar mensajes a otra clase sin añadir funcionalidad significativa, lo que añade complejidad innecesaria a la estructura del código.

## Ejemplo

### Problema

```java
public class Cliente {
    private String nombre;
    private String email;
    
    public Cliente(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getEmail() {
        return email;
    }
}

public class GestorDeNotificaciones {
    private ServicioEmail servicioEmail;
    
    public GestorDeNotificaciones() {
        this.servicioEmail = new ServicioEmail();
    }
    
    public void enviarEmailBienvenida(Cliente cliente) {
        servicioEmail.enviarEmailBienvenida(cliente);
    }
    
    public void enviarEmailFactura(Cliente cliente, String detallesFactura) {
        servicioEmail.enviarEmailFactura(cliente, detallesFactura);
    }
    
    public void enviarEmailRecordatorio(Cliente cliente, String mensaje) {
        servicioEmail.enviarEmailRecordatorio(cliente, mensaje);
    }
}

public class ServicioEmail {
    public void enviarEmailBienvenida(Cliente cliente) {
        String mensaje = "Bienvenido " + cliente.getNombre() + "! Gracias por registrarte.";
        enviar(cliente.getEmail(), "Bienvenida", mensaje);
    }
    
    public void enviarEmailFactura(Cliente cliente, String detallesFactura) {
        String mensaje = "Estimado " + cliente.getNombre() + ",\n\nAdjuntamos los detalles de su factura:\n" + detallesFactura;
        enviar(cliente.getEmail(), "Factura Mensual", mensaje);
    }
    
    public void enviarEmailRecordatorio(Cliente cliente, String mensaje) {
        String contenido = "Estimado " + cliente.getNombre() + ",\n\n" + mensaje;
        enviar(cliente.getEmail(), "Recordatorio", contenido);
    }
    
    private void enviar(String destinatario, String asunto, String contenido) {
        System.out.println("Enviando email a: " + destinatario);
        System.out.println("Asunto: " + asunto);
        System.out.println("Contenido: " + contenido);
        // Lógica real de envío de email
    }
}
```

En este ejemplo, `GestorDeNotificaciones` es claramente un "Middle Man" porque:

1. No añade ninguna funcionalidad propia
2. Simplemente reenvía todas las llamadas a `ServicioEmail`
3. Introduce una capa innecesaria entre los clientes y el servicio real
4. Aumenta la complejidad del código sin beneficios claros

### Solución propuesta

```java
public class Cliente {
    private String nombre;
    private String email;
    
    public Cliente(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getEmail() {
        return email;
    }
}

public class ServicioEmail {
    public void enviarEmailBienvenida(Cliente cliente) {
        String mensaje = "Bienvenido " + cliente.getNombre() + "! Gracias por registrarte.";
        enviar(cliente.getEmail(), "Bienvenida", mensaje);
    }
    
    public void enviarEmailFactura(Cliente cliente, String detallesFactura) {
        String mensaje = "Estimado " + cliente.getNombre() + ",\n\nAdjuntamos los detalles de su factura:\n" + detallesFactura;
        enviar(cliente.getEmail(), "Factura Mensual", mensaje);
    }
    
    public void enviarEmailRecordatorio(Cliente cliente, String mensaje) {
        String contenido = "Estimado " + cliente.getNombre() + ",\n\n" + mensaje;
        enviar(cliente.getEmail(), "Recordatorio", contenido);
    }
    
    private void enviar(String destinatario, String asunto, String contenido) {
        System.out.println("Enviando email a: " + destinatario);
        System.out.println("Asunto: " + asunto);
        System.out.println("Contenido: " + contenido);
        // Lógica real de envío de email
    }
}

// Código cliente que utiliza directamente el ServicioEmail
public class EjemploUso {
    public static void main(String[] args) {
        Cliente cliente = new Cliente("Juan Pérez", "juan@ejemplo.com");
        ServicioEmail servicioEmail = new ServicioEmail();
        
        // Usar servicio directamente sin intermediario
        servicioEmail.enviarEmailBienvenida(cliente);
        servicioEmail.enviarEmailFactura(cliente, "Total: $150.00");
    }
}
```

La solución elimina por completo la clase intermediaria `GestorDeNotificaciones` y permite que los clientes usen directamente `ServicioEmail`. Esto:

1. Reduce la cantidad de código y complejidad
2. Elimina una capa innecesaria
3. Simplifica el mantenimiento del código
4. Hace más directo el flujo de llamadas

Si en el futuro realmente se necesitara añadir funcionalidad adicional al proceso de notificación, se podría considerar extender `ServicioEmail` o crear una nueva clase con responsabilidades reales, en lugar de mantener un simple intermediario.