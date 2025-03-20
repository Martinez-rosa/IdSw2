# Message Chains

Ocurre cuando el código contiene una secuencia de llamadas encadenadas para acceder a un objeto o valor distante. Este smell code se manifiesta como una serie de invocaciones de método del tipo `a.getB().getC().getD().doSomething()`, lo que crea un alto acoplamiento entre diferentes partes del sistema y viola el Principio de Demeter ("Habla solo con tus amigos inmediatos").

## Ejemplo

### Problema

```java
public class Cliente {
    private String nombre;
    private Dirección dirección;
    
    public Cliente(String nombre, Dirección dirección) {
        this.nombre = nombre;
        this.dirección = dirección;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public Dirección getDirección() {
        return dirección;
    }
}

public class Dirección {
    private String calle;
    private Ciudad ciudad;
    
    public Dirección(String calle, Ciudad ciudad) {
        this.calle = calle;
        this.ciudad = ciudad;
    }
    
    public String getCalle() {
        return calle;
    }
    
    public Ciudad getCiudad() {
        return ciudad;
    }
}

public class Ciudad {
    private String nombre;
    private Provincia provincia;
    
    public Ciudad(String nombre, Provincia provincia) {
        this.nombre = nombre;
        this.provincia = provincia;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public Provincia getProvincia() {
        return provincia;
    }
}

public class Provincia {
    private String nombre;
    private String códigoPostal;
    
    public Provincia(String nombre, String códigoPostal) {
        this.nombre = nombre;
        this.códigoPostal = códigoPostal;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getCódigoPostal() {
        return códigoPostal;
    }
}

public class ServicioEnvíos {
    public void procesarEnvío(Cliente cliente) {
        // Cadena de mensajes para obtener el código postal
        String códigoPostal = cliente.getDirección().getCiudad().getProvincia().getCódigoPostal();
        
        System.out.println("Procesando envío para " + cliente.getNombre() + 
                           " al código postal " + códigoPostal);
        
        // Más cadenas de mensajes
        if (cliente.getDirección().getCiudad().getProvincia().getNombre().equals("Madrid")) {
            System.out.println("Envío prioritario para Madrid");
        }
        
        // Otra cadena para obtener el nombre de la ciudad
        String ciudad = cliente.getDirección().getCiudad().getNombre();
        System.out.println("Destino: " + ciudad);
    }
}
```

En este ejemplo, `ServicioEnvíos` contiene varias cadenas de mensajes para acceder a información distante a través de múltiples objetos. Esto genera varios problemas:

1. Alto acoplamiento entre clases: `ServicioEnvíos` necesita conocer toda la estructura interna desde `Cliente` hasta `Provincia`
2. Fragilidad ante cambios: si cualquier parte de la cadena cambia, el código se romperá
3. Dificultad de testing: las pruebas requerirán configurar una estructura completa de objetos
4. Violación del Principio de Demeter: cada objeto debería interactuar solo con sus "vecinos cercanos"
5. Difícil legibilidad y mantenimiento del código

### Solución propuesta

```java
public class Cliente {
    private String nombre;
    private Dirección dirección;
    
    public Cliente(String nombre, Dirección dirección) {
        this.nombre = nombre;
        this.dirección = dirección;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    // Métodos de delegación que evitan la cadena de mensajes
    public String getCódigoPostal() {
        return dirección.getCódigoPostal();
    }
    
    public String getNombreCiudad() {
        return dirección.getNombreCiudad();
    }
    
    public boolean esDeProvinciaCapital() {
        return dirección.esDeProvinciaCapital();
    }
}

public class Dirección {
    private String calle;
    private Ciudad ciudad;
    
    public Dirección(String calle, Ciudad ciudad) {
        this.calle = calle;
        this.ciudad = ciudad;
    }
    
    public String getCalle() {
        return calle;
    }
    
    public String getCódigoPostal() {
        return ciudad.getCódigoPostal();
    }
    
    public String getNombreCiudad() {
        return ciudad.getNombre();
    }
    
    public boolean esDeProvinciaCapital() {
        return ciudad.esDeProvinciaCapital();
    }
}

public class Ciudad {
    private String nombre;
    private Provincia provincia;
    
    public Ciudad(String nombre, Provincia provincia) {
        this.nombre = nombre;
        this.provincia = provincia;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getCódigoPostal() {
        return provincia.getCódigoPostal();
    }
    
    public boolean esDeProvinciaCapital() {
        return provincia.getNombre().equals("Madrid");
    }
}

public class Provincia {
    private String nombre;
    private String códigoPostal;
    
    public Provincia(String nombre, String códigoPostal) {
        this.nombre = nombre;
        this.códigoPostal = códigoPostal;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getCódigoPostal() {
        return códigoPostal;
    }
}

public class ServicioEnvíos {
    public void procesarEnvío(Cliente cliente) {
        // Sin cadenas de mensajes
        String códigoPostal = cliente.getCódigoPostal();
        
        System.out.println("Procesando envío para " + cliente.getNombre() + 
                           " al código postal " + códigoPostal);
        
        // Usando método que encapsula la lógica
        if (cliente.esDeProvinciaCapital()) {
            System.out.println("Envío prioritario para Madrid");
        }
        
        // Sin cadena de mensajes
        System.out.println("Destino: " + cliente.getNombreCiudad());
    }
}
```

La solución implementa el patrón "Método de Delegación" (también conocido como "Law of Demeter helpers") para eliminar las cadenas de mensajes:

1. **Métodos de delegación**: Cada clase implementa métodos que delegan en los objetos que contiene, ocultando la estructura interna.
1. **Encapsulamiento mejorado**: Las clases solo exponen la información necesaria sin revelar los detalles de su implementación interna.
1. **Reducción del acoplamiento**: La clase `ServicioEnvíos` ya no necesita conocer toda la estructura de objetos.
1. **Mejor testabilidad**: Las pruebas ahora son más simples ya que no requieren configurar estructuras complejas.
1. **Respeto al Principio de Demeter**: Cada objeto solo habla con sus vecinos inmediatos.
