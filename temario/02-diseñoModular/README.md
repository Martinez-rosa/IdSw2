# Diseño modular

## ¿Por qué?

En el desarrollo de software actual, frecuentemente se enfrentan proyectos que terminan con resultados insatisfactorios: sistemas que fallan en cumplir con los requisitos establecidos, sobrepasan los plazos planificados o exceden los presupuestos asignados. Esta situación refleja problemas fundamentales en la práctica del desarrollo de software.

El origen de estas dificultades puede identificarse en diversas manifestaciones de una calidad deficiente:

|Ineficacia|Ineficiencia|Inefectividad|
|-|-|-|
|No se consigue entregar el producto en el plazo establecido.|No se puede desarrollar el sistema dentro del coste previsto.|No se logra ofrecer la funcionalidad requerida en el ámbito acordado.|

Más allá de estas variables económicas, los sistemas desarrollados con frecuencia presentan problemas de calidad que afectan a todo su ciclo de vida:

- **Baja fiabilidad**: El sistema falla con frecuencia en entornos de producción.
- **Escasa usabilidad**: Los usuarios encuentran el sistema difícil de utilizar.
- **Insuficiente interoperabilidad**: El sistema no se integra adecuadamente con otros sistemas.
- **Pobre seguridad**: La aplicación presenta vulnerabilidades explotables.

Sin embargo, el problema más crítico y costoso a largo plazo es la **deficiente mantenibilidad**, que se manifiesta en sistemas:

- **Viscosos**: No se pueden entender con facilidad debido a su estructura compleja y desorganizada.
- **Rígidos**: No se pueden modificar con facilidad porque cualquier cambio requiere ajustes en múltiples lugares.
- **Frágiles**: No se pueden probar con facilidad porque las modificaciones provocan errores en partes aparentemente no relacionadas.
- **Inmóviles**: No se pueden reutilizar con facilidad porque sus componentes están fuertemente acoplados entre sí.

Estas deficiencias suelen ser el resultado de una **complejidad arbitraria** introducida por decisiones de diseño inadecuadas o por la ausencia de un diseño deliberado.

### ¿Pero, el [diseño](/temario/01-diseño/README.md) no basta?

Una creencia errónea pero común es que un diseño inspirado directamente en el modelo del dominio garantiza por sí mismo un código de calidad. Sin embargo, la experiencia demuestra que este enfoque puede resultar en sistemas con graves problemas estructurales:

- Clases que acumulan demasiadas responsabilidades por ser "expertos en información".
- Componentes acoplados a múltiples tecnologías (interfaz, persistencia, comunicaciones).
- Entidades que asumen responsabilidades que deberían estar distribuidas.

Los intentos de desarrollar un sistema de calidad sin principios de diseño sólidos suelen conducir a estructuras difíciles de entender, modificar, probar y reutilizar.

> [Ver ejemplo](ejemplo/diseñoBasadoMdD.md)

## ¿Qué?

El Diseño Modular constituye un enfoque fundamental para abordar los problemas anteriormente descritos. Se trata de un conjunto de principios y prácticas orientados a estructurar el software en componentes bien definidos, con responsabilidades claras e interacciones controladas.

Este enfoque tiene sus raíces en los trabajos pioneros de:

- **Larry Constantine**, quien definió las métricas de cohesión en la década de 1960.
- **David Parnas**, que publicó "Sobre los criterios que se utilizarán en la descomposición de sistemas en módulos" en 1972.
- **Larry Constantine, Ed Yourdon y Wayne Stevens**, quienes desarrollaron formalmente el concepto de Diseño Estructurado en 1974.
- **Ed Yourdon y Larry Constantine**, que consolidaron estos principios en su obra "Diseño Estructurado" en 1979.

El Diseño Modular se fundamenta en tres criterios fundamentales que debe cumplir todo módulo (sea método, clase o paquete):

1. **Alta cohesión**: Los elementos dentro de un módulo deben estar fuertemente relacionados entre sí y contribuir a un propósito unificado.
2. **Bajo acoplamiento**: Las dependencias entre módulos deben minimizarse para reducir el impacto de los cambios y facilitar la evolución independiente.
3. **Tamaño adecuado**: Cada componente debe tener dimensiones apropiadas para su nivel de abstracción, facilitando su comprensión y mantenimiento.

Estos tres principios, aplicados de manera consistente, constituyen la base para crear software que sea simultáneamente comprensible, modificable, testeable y reutilizable.

## ¿Para qué?

La aplicación sistemática de los principios del Diseño Modular produce sistemas con las siguientes características positivas:

<div align=center>

|Eficacia|Eficiencia|Efectividad|
|-|-|-|
|Se consigue entregar el producto en los plazos establecidos.|Se desarrolla el sistema dentro de los costos previstos.|Se logra cumplir con el ámbito funcional requerido.|

</div>

Más allá de las variables económicas, los sistemas desarrollados bajo estos principios presentan cualidades técnicas superiores:

- **Alta fiabilidad**: El sistema funciona consistentemente según lo esperado.
- **Buena usabilidad**: Los usuarios pueden interactuar con el sistema de manera intuitiva.
- **Adecuada interoperabilidad**: El sistema se integra fácilmente con otros sistemas.
- **Robusta seguridad**: La aplicación resiste intentos de explotación maliciosa.

Sin embargo, la ventaja más significativa y valiosa a largo plazo es la **excelente mantenibilidad**, que se manifiesta en sistemas:

- **Fluidos**: Se pueden entender con facilidad gracias a su estructura clara y organizada.
- **Flexibles**: Se pueden modificar con facilidad porque los cambios están localizados.
- **Robustos**: Se pueden probar con facilidad porque las modificaciones no provocan efectos secundarios inesperados.
- **Reusables**: Se pueden reutilizar componentes con facilidad porque están débilmente acoplados.

<div align=center>

| Diseño Modular Adecuado |||| Diseño Modular Deficiente |
|-|-:|:-:|:-|-|
|Comprensible               |**Fluidez**|     *vs*|**Viscosidad**  | Difícil de entender |
|Adaptable al cambio        |**Flexibilidad**|*vs*|**Rigidez**| Resistente a modificaciones |
|Resistente a fallos        |**Robustez**|*vs*|**Fragilidad**| Propenso a errores inesperados |
|Aprovechable en otros contextos|**Reusabilidad**|    *vs*|**Inmovilidad**  | Limitado a su contexto original |

</div>

Como expresa elocuentemente la analogía de la construcción de mosaicos:

> "Es difícil hacer cualquier mosaico con fichas grandes de varios colores (baja cohesión) y formas irregulares (alto acoplamiento). Es fácil hacer cualquier mosaico con fichas pequeñas de un solo color (alta cohesión) y formas cuadradas (bajo acoplamiento)."

## ¿Cómo?

La implementación efectiva del Diseño Modular requiere profundizar en sus tres principios fundamentales:

### Cohesión

La cohesión evalúa el grado en que los elementos dentro de un módulo se relacionan entre sí y contribuyen a un propósito unificado.

Un estudio detallado de este principio abarca:

- **Definición formal** de cohesión y sus implicaciones
- **Niveles de cohesión**, desde la coincidental (más baja) hasta la funcional (más alta)
- **Identificación de code smells** que señalan problemas de cohesión:
  - Envidia de características
  - Clases de datos
  - Cambios divergentes
  - Cirugía a escopetazos
  - Grupos de datos
  - Obsesión por tipos primitivos
  - Clases perezosas
- **Técnicas de refactorización** para mejorar la cohesión
- **Métricas** para evaluar objetivamente la cohesión

### Acoplamiento

El acoplamiento mide el grado de interdependencia entre los módulos de un sistema.

Una comprensión profunda de este principio incluye:

- **Definición formal** de acoplamiento y sus tipos
- **Acoplamiento aferente vs. eferente** y sus implicaciones
- **Acoplamiento directo vs. indirecto** y sus manifestaciones
- **Ley de demeter** como guía para reducir el acoplamiento
- **Identificación de code smells** relacionados con el acoplamiento:
  - Intimidad inapropiada
  - Cadenas de mensajes
  - Feature envy
  - Middle man
- **Métricas** para cuantificar el acoplamiento

### Tamaño

El principio de tamaño establece dimensiones óptimas para los diferentes componentes del software. Un análisis completo de este principio contempla:

- **Restricciones dimensionales** recomendadas para cada nivel:
  - Paquetes (~12-20 clases)
  - Clases (~20-25 métodos, ~200-500 líneas)
  - Métodos (~10-25 líneas, 2-3 parámetros)
- **Fundamentos cognitivos** que justifican estas restricciones
- **Complejidad ciclomática** como medida de complejidad del código
- **Identificación de code smells** vinculados a problemas de tamaño:
  - Métodos largos
  - Listas de parámetros largas
  - Clases grandes
  - Anidamiento excesivo
- **Técnicas de refactorización** específicas para reducir el tamaño
- **Herramientas de análisis** para monitorizar y controlar el tamaño

### Sinergia de los tres principios

La verdadera potencia del Diseño Modular surge cuando estos tres principios se aplican de manera conjunta y coordinada:

- La **alta cohesión** facilita el **bajo acoplamiento** al concentrar responsabilidades relacionadas
- El **bajo acoplamiento** permite mantener un **tamaño adecuado** al separar claramente las preocupaciones
- El **tamaño adecuado** promueve la **alta cohesión** al limitar la cantidad de responsabilidades que puede asumir un módulo

## ¿Y ahora qué?

Para comprender los fundamentos del diseño modular, se recomienda:

1. **Profundizar en los principios fundamentales**:
   - **[Cohesión](cohesion.md)**: Para entender cómo crear componentes con responsabilidades enfocadas
   - **[Acoplamiento](acoplamiento.md)**: Para aprender a minimizar dependencias entre componentes
   - **[Tamaño](tamaño.md)**: Para dominar el arte de dimensionar adecuadamente cada elemento

2. **Explorar las técnicas complementarias**:
   - **[Jerarquización](jerarquizacion.md)**: Para estructurar sistemas complejos en niveles comprensibles
   - **[Abstracción de interfaz](abstraccionInterfaz.md)**: Para definir contratos claros entre componentes
   - **[Diseño por contrato](diseñoContrato.md)**: Para formalizar precondiciones, postcondiciones e invariantes
   - **[Patrones de indirección](patronesIndireccion.md)**: Para implementar mecanismos específicos de desacoplamiento

3. **Aplicar estos principios y técnicas** de manera práctica en proyectos reales, utilizando refactorizaciones sistemáticas para mejorar diseños existentes.

4. **Integrar herramientas de análisis** que permitan evaluar objetivamente la calidad del diseño según estos principios.

El dominio del Diseño Modular no es un destino final sino un viaje continuo hacia la excelencia en el desarrollo de software, cuyos beneficios se manifestarán en cada fase del ciclo de vida de los sistemas construidos bajo sus principios.
