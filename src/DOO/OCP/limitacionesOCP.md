# Limitaciones OCP

<div align=center>

||||
|-|-|-|
|Supongamos que la universidad necesita implementar un sistema de becas y ayudas financieras.|Cada tipo de alumno tiene requisitos específicos y procedimientos diferentes para la solicitud y evaluación de becas.|Además, existen diferentes tipos de evaluadores de becas, cada uno especializado en un tipo particular de alumno y con criterios específicos.

</div>

## Fase 1: [Intento inicial con comprobaciones de tipo](OCP04)

```java
public class EvaluadorBecas {
    public void evaluarSolicitud(Alumno alumno) {
        System.out.println("Evaluando solicitud de beca para: " + alumno.getNombre());
        
        // Mal diseño: uso de instanceof para diferenciar comportamiento
        if (alumno instanceof AlumnoErasmus) {
            AlumnoErasmus erasmus = (AlumnoErasmus) alumno;
            System.out.println("Aplicando criterios especiales para Erasmus de " + erasmus.getPaisOrigen());
            // Lógica específica para alumnos Erasmus
            if (erasmus.getPaisOrigen().equals("Italia")) {
                System.out.println("Beca de movilidad europea concedida");
            } else {
                System.out.println("Aplicando convenio internacional para " + erasmus.getPaisOrigen());
            }
        } 
        else if (alumno instanceof AlumnoVirtual) {
            AlumnoVirtual virtual = (AlumnoVirtual) alumno;
            System.out.println("Aplicando criterios para educación a distancia");
            // Lógica específica para alumnos virtuales
            if (virtual.requiereExamenesPresenciales()) {
                System.out.println("Beca para desplazamiento a exámenes concedida");
            } else {
                System.out.println("Beca para recursos digitales concedida");
            }
        } 
        else {
            System.out.println("Aplicando criterios estándar para alumnos regulares");
            // Lógica para alumnos regulares
        }
    }
}
```

## Fase 2: El problema se complica

Ahora, la universidad decide añadir varios tipos más de alumnos (AlumnoInvestigador, AlumnoDeportista, AlumnoDiscapacidad) y varios tipos de evaluadores de becas (EvaluadorAcademico, EvaluadorSocioeconomico, EvaluadorDeportes, EvaluadorInternacional). Cada evaluador debe aplicar criterios específicos según el tipo de alumno.

El código empieza a volverse inmanejable con comprobaciones anidadas:

```java
// Código que se vuelve muy complejo y propenso a errores
public void evaluar(Alumno alumno, TipoEvaluador tipoEvaluador) {
    if (tipoEvaluador == TipoEvaluador.ACADEMICO) {
        if (alumno instanceof AlumnoErasmus) {
            // Lógica específica
        } else if (alumno instanceof AlumnoVirtual) {
            // Lógica específica
        } else if (alumno instanceof AlumnoInvestigador) {
            // Lógica específica
        } 
        // ... más condiciones
    } 
    else if (tipoEvaluador == TipoEvaluador.SOCIOECONOMICO) {
        // Otro bloque de condiciones anidadas
        if (alumno instanceof AlumnoErasmus) {
            // Lógica específica
        }
        // ... más condiciones
    }
    // ... más tipos de evaluadores
}
```

### Fase 3: Solución con doble despacho

Para resolver este problema, implementamos el patrón de doble despacho:

```java
// 1. Interfaz para el evaluador de becas
interface EvaluadorBecas {
    void evaluar(Alumno alumno);
    void evaluar(AlumnoErasmus alumno);
    void evaluar(AlumnoVirtual alumno);
    void evaluar(AlumnoInvestigador alumno);
    // Se añaden nuevos métodos por cada tipo de alumno
}

// 2. Modificamos la clase base Alumno para aceptar un evaluador
public class Alumno {
    // Atributos y métodos anteriores...
    
    // Método para aceptar un evaluador (primer despacho)
    public void solicitarBeca(EvaluadorBecas evaluador) {
        evaluador.evaluar(this); // Delegamos la evaluación al evaluador
    }
}

// 3. Cada subclase de Alumno sobreescribe el método solicitarBeca
public class AlumnoErasmus extends Alumno {
    // Atributos y métodos anteriores...
    
    @Override
    public void solicitarBeca(EvaluadorBecas evaluador) {
        evaluador.evaluar(this); // Invoca la versión específica para AlumnoErasmus
    }
}

public class AlumnoVirtual extends Alumno {
    // Atributos y métodos anteriores...
    
    @Override
    public void solicitarBeca(EvaluadorBecas evaluador) {
        evaluador.evaluar(this); // Invoca la versión específica para AlumnoVirtual
    }
}

// 4. Implementaciones concretas de evaluadores
public class EvaluadorAcademico implements EvaluadorBecas {
    @Override
    public void evaluar(Alumno alumno) {
        System.out.println("Evaluando expediente académico estándar para " + alumno.getNombre());
        // Lógica para alumno regular
    }
    
    @Override
    public void evaluar(AlumnoErasmus alumno) {
        System.out.println("Evaluando expediente internacional para " + alumno.getNombre() + 
                          " de " + alumno.getPaisOrigen());
        // Lógica específica para Erasmus
    }
    
    @Override
    public void evaluar(AlumnoVirtual alumno) {
        System.out.println("Evaluando rendimiento online para " + alumno.getNombre());
        // Lógica específica para virtuales
    }
    
    @Override
    public void evaluar(AlumnoInvestigador alumno) {
        System.out.println("Evaluando publicaciones y proyectos para " + alumno.getNombre());
        // Lógica específica para investigadores
    }
}

public class EvaluadorSocioeconomico implements EvaluadorBecas {
    // Implementaciones específicas para cada tipo de alumno...
    
    @Override
    public void evaluar(AlumnoErasmus alumno) {
        System.out.println("Aplicando baremos internacionales para " + alumno.getNombre());
        // Comprobaciones específicas según país de origen
    }
    
    // Más implementaciones...
}

// 5. Uso del sistema
public class Universidad {
    // Métodos anteriores...
    
    public void procesarSolicitudBeca(Alumno alumno, EvaluadorBecas evaluador) {
        System.out.println("Procesando solicitud de beca...");
        alumno.solicitarBeca(evaluador); // Aquí ocurre el doble despacho
    }
}

// Código cliente
public class Cliente {
    public static void main(String[] args) {
        Universidad universidad = new Universidad();
        
        Alumno regular = new Alumno("A001", "Carlos García", "carlos@email.com");
        AlumnoErasmus erasmus = new AlumnoErasmus("E001", "Sophie Martin", "sophie@email.com", "Francia", "Universidad de París");
        AlumnoVirtual virtual = new AlumnoVirtual("V001", "Elena López", "elena@email.com", "Campus Virtual", false);
        
        EvaluadorBecas evaluadorAcademico = new EvaluadorAcademico();
        EvaluadorBecas evaluadorSocioeconomico = new EvaluadorSocioeconomico();
        
        universidad.procesarSolicitudBeca(regular, evaluadorAcademico);
        universidad.procesarSolicitudBeca(erasmus, evaluadorAcademico);
        universidad.procesarSolicitudBeca(erasmus, evaluadorSocioeconomico);
        universidad.procesarSolicitudBeca(virtual, evaluadorAcademico);
    }
}
```

## Explicación del doble despacho

1. **Primer despacho**: Cuando llamamos a `alumno.solicitarBeca(evaluador)`, el tipo concreto de alumno determina qué método `solicitarBeca` se ejecuta.

2. **Segundo despacho**: Dentro de `solicitarBeca`, la llamada `evaluador.evaluar(this)` selecciona la implementación correcta de `evaluar` basada en el tipo concreto de `evaluador` y en el tipo concreto del alumno (debido a la sobrecarga de métodos).

## Ventajas de esta solución

1. **Elimina las comprobaciones de tipo (`instanceof`)** y los castings explícitos.

2. **Desacopla la lógica específica** de cada combinación alumno-evaluador.

3. **Extensible**: Podemos añadir nuevos tipos de alumnos y evaluadores sin modificar el código existente:
   - Para añadir un nuevo tipo de alumno, creamos una nueva subclase y añadimos un método en la interfaz `EvaluadorBecas`.
   - Para añadir un nuevo evaluador, implementamos la interfaz `EvaluadorBecas`.

4. **Cumple con el principio Open/Closed**: El sistema está abierto para extensión pero cerrado para modificación.

    *Este ejemplo muestra cómo un sistema que inicialmente podía funcionar con herencia simple puede evolucionar hasta necesitar patrones más complejos como el doble despacho, especialmente cuando tenemos que manejar comportamientos específicos para combinaciones de tipos.*