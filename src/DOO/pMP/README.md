# Patrón método-plantilla

|||
|-|-|
Se dificulta la extracción de un factor común en los códigos de los métodos de las clases derivadas por detalles inmersos en el propio código pero respetando un esquema general|Definir el esqueleto de un algoritmo de un método, diferir algunos pasos para las clases derivadas.

El patron permite que las clases derivadas redefinan esos pasos abstractos sin cambiar la estructura del algoritmo de la clase padre

|Sin método plantilla|Con método plantilla|
|:-:|:-:|
[🗒️](https://github.com/mmasias/23-24-pyKlondike/tree/449affaad73a3e49b27783e1c24488011c03a1ec/src)|[🗒️](https://github.com/mmasias/23-24-pyKlondike/tree/d66842e18ebf1473c12323aed6c4869dbb99e4da/src)
`mostrar()` en cada clases derivadas|`mostrar()` en clase padre, `mostrarContenido()` en clases derivadas
