# Ejemplo concreto de violación del principio de sustitución de Liskov (LSP)

El siguiente ejemplo está diseñado para ilustrar de manera concreta y directa cómo diversas formas de especialización de una clase pueden violar el Principio de Sustitución de Liskov (LSP), a pesar de mantener una relación de herencia estructural.

Se trata de un sistema bancario simplificado, donde modelamos diferentes tipos de cuentas que heredan de una clase base `CuentaBancaria`.

## Escenario general

La clase `CuentaBancaria` representa una cuenta simple que permite realizar depósitos y retiros, y consultar el saldo.

El contrato implícito de esta clase establece que:

- Los depósitos deben ser positivos.
- Los retiros deben ser positivos y no superar el saldo disponible.
- El saldo se reduce exactamente por el monto retirado.
- El saldo nunca puede ser negativo.

Se espera que cualquier clase derivada pueda utilizarse de forma intercambiable donde se espera una `CuentaBancaria`, sin necesidad de conocer detalles internos de la subclase.

## Variantes derivadas

Se han implementado tres subclases que representan especializaciones comunes en productos bancarios:

- `CuentaLimitada`: impone un límite máximo de retiros. Una vez alcanzado, no permite más operaciones de retiro.
- `CuentaComision`: aplica una comisión fija por cada retiro, reduciendo el saldo en una cantidad mayor que la solicitada.
- `CuentaSobregiro`: permite realizar retiros que exceden el saldo disponible, hasta un cierto límite de crédito, permitiendo que el saldo sea negativo.

Estas subclases, aunque técnicamente heredan de `CuentaBancaria`, introducen cambios que afectan los contratos de uso esperados por los clientes que operan sobre el tipo base: cada una viola el LSP de una forma distinta.

## Cliente genérico

Para verificar el cumplimiento del principio, se ha definido una clase `Cliente` que interactúa con instancias de `CuentaBancaria`, ejecutando una secuencia de operaciones sin conocer el tipo concreto de la cuenta. Este cliente:

1. Realiza varios retiros consecutivos sobre la misma cuenta.
2. Verifica si el saldo se comporta de forma coherente con las operaciones.
3. Compara el saldo esperado con el saldo real.

## Problemas

Cuando se sustituyen objetos de `CuentaBancaria` por instancias de las subclases, se observan fallos de comportamiento:

- En `CuentaLimitada`, los retiros fallan tras un número límite, rompiendo la precondición esperada.
- En `CuentaComision`, el saldo se reduce más de lo previsto, rompiendo la postcondición de equivalencia entre lo retirado y lo deducido.
- En `CuentaSobregiro`, el saldo puede volverse negativo, violando una invariante fundamental del tipo base.

Estas inconsistencias contractuales en un sistema real indicarían **mal diseño o falta de abstracción adecuada**.

## *2Think*

Este ejemplo muestra que **la herencia no garantiza sustitución segura**. Cada subclase rompe una parte distinta del contrato observable por el cliente:

- `CuentaLimitada`: **precondición más estricta**.
- `CuentaComision`: **postcondición más débil**.
- `CuentaSobregiro`: **invariante alterada**.

Por tanto, nos debe quedar claro que:

||||
|-|-|-|
|**Que herencia estructural no basta**|El hecho de que todas las clases extiendan `CuentaBancaria` no significa que **compartan el mismo comportamiento público esperado por el cliente**. El código compila, pero rompe principios de diseño. Esto nos obliga a preguntar:|¿Realmente todas estas clases deberían heredar de `CuentaBancaria`?
|**Que los contratos están mezclados con la lógica**|`CuentaBancaria` tiene un contrato implícito: no saldo negativo, retiros exactos, sin comisiones, etc. Pero este contrato **no está formalizado como interfaz de comportamiento**, por lo que **no hay manera de hacer cumplir ese contrato** si una subclase lo viola. Esto sugiere:|Tal vez `CuentaBancaria` debería ser una interfaz o clase abstracta con contratos explícitos, o tal vez el diseño debería usar composición.
|**Que el diseño actual une modelos de negocio incompatibles**|Los tres productos bancarios (`Limitada`, `Comision`, `Sobregiro`) tienen reglas de negocio distintas. Forzarlas dentro de una misma jerarquía **viola el Principio de Responsabilidad Única y el LSP al mismo tiempo**.|En vez de una jerarquía única, podríamos tener decoradores o políticas externas (estrategias) para limitar retiros, calcular comisiones o gestionar sobregiros.

### ¿Qué hacer?

1. **Usar composición en lugar de herencia**
   * `CuentaBancaria` podría tener una política de retiro (interfaz `PoliticaRetiro`) que defina cómo se retira dinero.
   * Las variantes (`ConComision`, `ConLimite`, `ConSobregiro`) serían decoradores o estrategias, y no tendrían que violar el contrato base.
2. **Modelar contratos como parte explícita del diseño**
   * Usar interfaces separadas.
3. **Separar productos incompatibles**
   * `CuentaBancariaConSobregiro` podría no heredar de `CuentaBancaria`, sino ser un tipo aparte que comparte una interfaz común, pero no una implementación.

> [*Una propuesta*](/src/DOO/LSP/LSP01/)