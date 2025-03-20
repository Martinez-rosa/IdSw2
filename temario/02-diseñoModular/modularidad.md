# Modularidad

## ¿Por qué?

## ¿Qué?

Es un conjunto de principios y prácticas orientadas a estructurar el software en componentes bien definidos, con responsabilidades claras e interacciones controladas, fundamentándose en tres criterios fundamentales que debe cumplir todo módulo (sea método, clase o paquete):

1. **Alta cohesión**: Los elementos dentro de un módulo deben estar fuertemente relacionados entre sí y contribuir a un propósito unificado.
2. **Bajo acoplamiento**: Las dependencias entre módulos deben minimizarse para reducir el impacto de los cambios y facilitar la evolución independiente.
3. **Tamaño adecuado**: Cada componente debe tener dimensiones apropiadas para su nivel de abstracción, facilitando su comprensión y mantenimiento.

## ¿Para qué?

## ¿Cómo?

### Número de módulos

<div align=center>

|Costes de la modularización|Es un compromiso, un equilibrio|
|-|-|
|- el ***coste de desarrollo*** de cada módulo, pocos muy grandes es más costoso que muchos muy pequeños<br>- el ***coste de integración*** de todos los módulos, muy pocos cuesta poco y muchos cuesta mucho|![](/images/compromisosModularizacion.jpg)

</div>

Se parte un problema para ser efectivos, eficaces y eficientes, resolviendo problemas más pequeños, **¡pero cuando el problema requiere partirse y no antes**!

<div align=center>

|Aplicación mediana (100.000 LoC)|Muchos módulos|Número equilibrado de módulos|Pocos módulos
|-|:-:|:-:|:-:|
|Costes de Desarrollo|**Reducido**|*Equilibrado*|Disparado
|Costes de Integración|Disparado|*Equilibrado*|**Reducido**
|Costes Totales|Disparado|*Equilibrado*|Disparado

</div>

### Distribución de responsabilidades

<div align=center>

||||
|-|:-:|-|
|![](/images/modularizacionMalDistribuida.png)|También de forma equilibrada,<br>con una media y una desviación típica<br>reducidas de la carga relativa de la responsabilidad total:<br>se trata de partir el problema,<br>no de trasladarlo!|![](/images/modularizacionBienDistribuida.png)|

</div>

