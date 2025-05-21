# Segregación de interfaces

El Principio de Segregación de Interfaces fue introducido por Robert C. Martin como parte de los principios SOLID. Este principio establece que es mejor tener muchas interfaces específicas que una sola interfaz de propósito general, lo que reduce el acoplamiento entre componentes y aumenta la cohesión.

## Beneficios

- Reduce la fragilidad del sistema al minimizar las dependencias innecesarias.
- Mejora la mantenibilidad del código al asegurar que las clases solo implementen los métodos que realmente necesitan.
- Facilita la extensibilidad del sistema mediante interfaces más cohesivas.
- Permite un diseño más claro con responsabilidades bien definidas.

> Una clase no debe verse obligada a implementar interfaces que no utiliza
>
> *==>  interfaces deben ser pequeñas y específicas*
>

## Signos de violación del ISP

- Métodos vacíos o con implementaciones triviales que solo lanzan excepciones
- Comentarios como "// No aplica" o "// No implementado"
- Clases que implementan métodos que nunca utilizan
- Interfaces con muchos métodos no relacionados entre sí

## Consideraciones de diseño

- Equilibrar entre tener demasiadas interfaces pequeñas (fragmentación excesiva) y pocas interfaces grandes (acoplamiento excesivo)
- Pensar en las interfaces desde la perspectiva del cliente que las utilizará
- Considerar la evolución del sistema al diseñar las interfaces


```java

interface Objeto {
    void mover();
    void hablar();
    void cazar();
    void limpiar();
}

class Gato implements Objeto {
    @Override
    public void mover() {
        System.out.println("El gato se está moviendo");
    }

    @Override
    public void hablar() {
        System.out.println("El gato dice: Miau!");
    }

    @Override
    public void cazar() {
        System.out.println("El gato está cazando");
    }

    @Override
    public void limpiar() {
        System.out.println("El gato no limpia");
    }    
}

class Aspiradora implements Objeto {
    @Override
    public void mover() {
        System.out.println("La aspiradora se está moviendo");
    }

    @Override
    public void hablar() {
        System.out.println("La aspiradora hace ruido");
    }

    @Override
    public void cazar() {
        System.out.println("La aspiradora no caza");
    }

    @Override
    public void limpiar() {
        System.out.println("La aspiradora limpia");
    } 
}

class Mapa {
    // ...
}
```

---

```java
java// Interfaces segregadas
interface Movible {
    void mover();
}

interface Hablante {
    void hablar();
}

interface Cazador {
    void cazar();
}

interface Limpiador {
    void limpiar();
}

// Implementaciones
class Gato implements Movible, Hablante, Cazador {
    @Override
    public void mover() {
        System.out.println("El gato se está moviendo sigilosamente");
    }

    @Override
    public void hablar() {
        System.out.println("El gato dice: Miau!");
    }

    @Override
    public void cazar() {
        System.out.println("El gato está cazando un ratón");
    }
}

class Aspiradora implements Movible, Limpiador {
    @Override
    public void mover() {
        System.out.println("La aspiradora se está moviendo por el suelo");
    }
    
    @Override
    public void limpiar() {
        System.out.println("La aspiradora está recogiendo polvo");
    }
}

class Robot implements Movible, Hablante, Limpiador {
    @Override
    public void mover() {
        System.out.println("El robot se desplaza con ruedas");
    }
    
    @Override
    public void hablar() {
        System.out.println("El robot dice: Hola humano");
    }
    
    @Override
    public void limpiar() {
        System.out.println("El robot está limpiando la superficie");
    }
}
```

[Un ejemplo más completo](SOLID_I_ejemploMasCompleto.md)