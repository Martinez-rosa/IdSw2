# Principio de sustitución de Liskov

> La **notación formal del Principio de Sustitución de Liskov (LSP)** fue definida en términos de *teoría de tipos* por Barbara Liskov y Jeannette Wing en su artículo clásico de 1994: **"A Behavioral Notion of Subtyping"**.
> 
> $$
> S <: T \iff \forall P[\_:T],\ P[x:T] \equiv obs P[y:S]
> $$
> 
> Es decir:
> **Para todo programa `P` que sea correcto con una variable de tipo `T`, al sustituir esa variable con un objeto de tipo `S`, el comportamiento observable de `P` no debe cambiar (es decir, sus efectos observables deben ser equivalentes).**
> 

<div align=center>

|Se busca establecer que:|||
|-|-|-|
|*Si para cada objeto oT de un tipo T,<br>hay un objeto oS de tipo S tal que<br>para todo programa P definido en términos de T<br>el comportamiento de P no cambia<br>cuando oT es sustituido por oS,<br>entonces S es un subtipo de T*|![](/images/modelosUML/preLiskov.svg)|![](/images/modelosUML/postLiskov.svg)|

</div>

## ¿Qué implica?

Para que `S` sea un subtipo válido de `T`, deben cumplirse las siguientes condiciones:

<div align=center>

|Las...|Deben cumplir con|Esto es (formalmente)|Definidas por la clase base...|
|-|-|-|-|
|**Precondiciones**|No ser más fuertes|Un método sobrescrito en `S` no debe requerir más de lo que requiere en `T`.|Las clases derivadas deben mantenerlas iguales o hacerlas más débiles (menos restrictivas)<br>Las clases derivadas no pueden añadir nuevas restricciones ni fortalecer las existentes
|**Postcondiciones**|No ser más débiles|Un método sobrescrito en `S` debe cumplir al menos lo mismo que en `T`.|Las clases derivadas deben mantenerlas iguales o hacerlas más fuertes (más restrictivas)<br>Las clases derivadas no pueden debilitar las garantías que ofrece la clase base
|**Invariantes**|Preservarse|Las invariantes definidas en `T` deben seguir cumpliéndose en `S`.|Las clases derivadas deben preservarlas en su totalidad<br>Las clases derivadas no pueden modificarlas, ni para fortalecerlas ni para debilitarlas
|**Tipos de excepciones** |Ser compatibles|`S` no debe lanzar excepciones inesperadas respecto a lo que `T` promete.|Las clases derivadas deben lanzar las mismas excepciones o subtipos de ellas<br>Las clases derivadas no pueden introducir nuevas excepciones no declaradas por la clase base

</div>

