# Zoológico - Versión 0: Uso de instanceof

<div align=center>

![](/images/temario/03-diseñoOO/src/01-DD/v000/v000.svg)

</div>

## Descripción

En esta primera versión, usamos una única clase `Animal` con un campo `tipo` para diferenciar entre perros y pájaros. El `Cuidador` identifica el tipo de animal usando condicionales para ejecutar comportamientos específicos.

```java
if (animal.getTipo().equals("Pajaro")) {
    System.out.println("Le doy semillas especiales para aves");
    // ...
} else if (animal.getTipo().equals("Perro")) {
    System.out.println("Le doy croquetas premium");
    // ...
}
```

## Problemas identificados

1. **Violación del Principio Abierto/Cerrado (OCP)**:
   - Cada vez que agregamos un nuevo tipo de animal (por ejemplo, "Gato"), debemos modificar el código en todas las clases que verifican el tipo.
   - Se acumula lógica condicional basada en tipos específicos.

2. **Acoplamiento fuerte**:
   - El `Cuidador` conoce los detalles de implementación de cada tipo de animal.
   - Este acoplamiento hace que el código sea difícil de mantener y extender.

3. **Código duplicado**:
   - Los condicionales para comprobar los tipos se repiten en cada método que necesita comportamiento específico por tipo.

4. **Difícil de extender**:
   - Añadir un nuevo tipo de animal requiere modificar todos los condicionales existentes.