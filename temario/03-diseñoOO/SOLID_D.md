# Inversión de dependencias

El Principio de Inversión de Dependencias (DIP) es el último de los principios SOLID, propuesto también por Robert C. Martin. Establece dos reglas fundamentales:

- Los módulos de alto nivel no deben depender de módulos de bajo nivel. Ambos deben depender de abstracciones.
- Las abstracciones no deben depender de los detalles. Los detalles deben depender de las abstracciones.

## Beneficios

- Facilita el desarrollo de sistemas desacoplados y más fáciles de mantener
- Permite la sustitución de implementaciones sin afectar el código cliente
- Simplifica la implementación de pruebas unitarias mediante mocks o stubs
- Promueve la extensibilidad al permitir agregar nuevas implementaciones sin modificar código existente
- Mejora la modularidad y la reutilización de componentes

## Signos de violación del DIP

- Código que crea instancias directamente con new dentro de clases de alto nivel
- Dependencias directas hacia clases concretas en lugar de interfaces
- Dificultad para reemplazar una implementación por otra sin modificar código
- Complejidad para realizar pruebas unitarias

## Consideraciones prácticas

- Evitar la sobreingeniería: aplicar DIP cuando realmente se necesita flexibilidad
- Balancear entre abstracción y simplicidad
- Las dependencias estables (como la biblioteca estándar) a veces no necesitan ser invertidas

## Técnicas de implementación

- ***Inyección de dependencias***: Proporcionar las dependencias a un objeto en lugar de permitir que las cree
  - Por constructor (preferida): Como se muestra en el ejemplo
  - Por setter: mapa.setObjetosMovibles(listaDeObjetos);
  - Por método: mapa.configurarObjetos(listaDeObjetos);
- ***Contenedores IoC (Inversión de Control)***: Frameworks como Spring que manejan la creación y gestión de dependencias automáticamente
- ***Factories***: Métodos o clases que se encargan de la creación de instancias

---

```java

public class Simulacion {

    public static void main(String[] args) {
        Mapa mapa = new Mapa();

        while (true){
            mapa.moverGato();
            mapa.moverAspiradora();
        }
    }
}

class Mapa {

    private Gato gato;
    private Aspiradora aspiradora;

    public Mapa() {
        this.gato = new Gato();
        this.aspiradora = new Aspiradora();
    }

    public void moverGato() {
        gato.mover();
    }

    public void moverAspiradora() {
        aspiradora.mover();
    }
}

class Gato {
    public void mover() {
        System.out.println("El gato se está moviendo");
    }
}

class Aspiradora {
    public void mover() {
        System.out.println("La aspiradora se está moviendo");
    }
}
```

---

```java

public class Simulacion {
    public static void main(String[] args) {
        // Creación de objetos concretos
        ObjetoMovible gato = new Gato();
        ObjetoMovible aspiradora = new Aspiradora();
        ObjetoMovible robot = new Robot();
        
        // Creación de colecciones de objetos movibles
        List<ObjetoMovible> objetosMovibles = new ArrayList<>();
        objetosMovibles.add(gato);
        objetosMovibles.add(aspiradora);
        objetosMovibles.add(robot);
        
        // Inyección de dependencias a través del constructor
        Mapa mapa = new Mapa(objetosMovibles);
        
        // Iniciar simulación
        mapa.ejecutarSimulacion();
    }
}

class Mapa {
    private final List<ObjetoMovible> objetosMovibles;
    
    // Constructor que recibe la dependencia como abstracción
    public Mapa(List<ObjetoMovible> objetosMovibles) {
        this.objetosMovibles = objetosMovibles;
    }
    
    // Método que trabaja con la abstracción, no con implementaciones concretas
    public void ejecutarSimulacion() {
        while (true) {
            moverTodosLosObjetos();
            // Añadir lógica adicional de simulación
            try {
                Thread.sleep(1000); // Pausa de 1 segundo entre iteraciones
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    private void moverTodosLosObjetos() {
        for (ObjetoMovible objeto : objetosMovibles) {
            objeto.mover();
        }
    }
}

// Interfaz que define el contrato (abstracción)
interface ObjetoMovible {
    void mover();
}

// Implementaciones concretas
class Gato implements ObjetoMovible {
    @Override
    public void mover() {
        System.out.println("El gato se está moviendo sigilosamente");
    }
}

class Aspiradora implements ObjetoMovible {
    @Override
    public void mover() {
        System.out.println("La aspiradora se está desplazando por el suelo");
    }
}

class Robot implements ObjetoMovible {
    @Override
    public void mover() {
        System.out.println("El robot se mueve mediante ruedas omnidireccionales");
    }
}

```

---
