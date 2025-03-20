# Incomplete Library Class

Ocurre cuando una clase de una biblioteca externa no proporciona la funcionalidad necesaria y no puede ser modificada porque no se tiene control sobre su código fuente. Este problema puede requerir que se extiendan estas clases de biblioteca de maneras que pueden ser subóptimas o complicadas, potencialmente llevando a un diseño de software frágil y difícil de mantener.

## Ejemplo

Supongamos que se está utilizando una biblioteca de terceros para manejar conexiones de red, pero esta biblioteca no ofrece soporte adecuado para ciertos protocolos de seguridad que son necesarios para el proyecto.

### Problema

```java

public class NetworkConnector {
    private ThirdPartyNetworkHandler networkHandler;

    public NetworkConnector() {
        this.networkHandler = new ThirdPartyNetworkHandler();
    }

    public void connect(String url) {
        networkHandler.establishConnection(url);
        System.out.println("Connection established to " + url);
    }

    public void disconnect() {
        networkHandler.terminateConnection();
        System.out.println("Connection terminated.");
    }
}

```

La clase ThirdPartyNetworkHandler de la biblioteca externa maneja las conexiones de red, pero no proporciona métodos para configurar capas de seguridad específicas (como TLS o SSL) que son críticas para la aplicación. Dado que no se puede modificar directamente, se necesita encontrar una manera de extender o adaptar esta funcionalidad sin alterar la biblioteca.

### Solución inadecuada

Heredar y sobrecargar

```java
public class EnhancedNetworkHandler extends ThirdPartyNetworkHandler {
    
    public void establishSecureConnection(String url, String protocol) {
        if (protocol.equalsIgnoreCase("TLS")) {
            setupTLS();
        }
        // Llamamos al método padre
        super.establishConnection(url);
    }
    
    private void setupTLS() {
        System.out.println("TLS security context set up.");
    }
    
    // Añadimos más métodos que la biblioteca no tiene
    public boolean checkConnectionStatus() {
        System.out.println("Checking connection status...");
        return true;
    }
}
```

¿Por qué es un problema?

1. Si la biblioteca cambia en el futuro, nuestra clase heredada podría romperse
1. Estamos acoplados fuertemente a la implementación de la biblioteca
1. Difícil de testear por la herencia directa

Además, si la clase ThirdPartyNetworkHandler fuera final, esta solución no sería posible

### Solución propuesta

Una solución efectiva para tratar con clases de biblioteca incompletas es implementar un patrón de diseño que permita extender su funcionalidad indirectamente. Uno de estos patrones es el Adapter Pattern, que permite envolver la clase de la biblioteca con una nueva clase que proporciona la funcionalidad adicional requerida.

```java

public class SecureNetworkConnector {
    private ThirdPartyNetworkHandler networkHandler;

    public SecureNetworkConnector() {
        this.networkHandler = new ThirdPartyNetworkHandler();
    }

    public void connect(String url, String protocol) {
        if (protocol.equalsIgnoreCase("TLS")) {
            setupTLS();
        }
        networkHandler.establishConnection(url);
        System.out.println("Secure connection established to " + url + " using " + protocol);
    }

    private void setupTLS() {
        // Configura el contexto TLS antes de establecer la conexión
        System.out.println("TLS security context set up.");
    }

    public void disconnect() {
        networkHandler.terminateConnection();
        System.out.println("Connection terminated.");
    }
}

public class ThirdPartyNetworkHandler {
    public void establishConnection(String url) {
        System.out.println("Establishing connection...");
    }

    public void terminateConnection() {
        System.out.println("Terminating connection...");
    }
}


```

- **Extensibilidad mejorada**: Al envolver la clase de la biblioteca en una clase adaptadora, se proporciona la funcionalidad adicional requerida sin modificar el código de la biblioteca.
- **Mantenimiento simplificado**: La clase adaptadora actúa como un punto de control único para las extensiones de funcionalidad, lo que simplifica el mantenimiento y las actualizaciones futuras.
- **Separación de preocupaciones**: Se separa la lógica de conexión básica de la configuración de seguridad, lo que mejora la claridad del diseño y facilita pruebas más específicas y dirigidas.

Implementar el Adapter Pattern permite manejar eficazmente las limitaciones de las clases de biblioteca incompletas, proporcionando una capa de abstracción que puede ser fácilmente modificada para ajustarse a las necesidades del proyecto sin depender de cambios en la biblioteca externa.
