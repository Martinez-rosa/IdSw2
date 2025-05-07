# El peligro silencioso de las violaciones LSP

Cuando una violación de LSP produce un error inmediato (como una excepción), al menos el problema se hace evidente de inmediato. Sin embargo, los casos realmente problemáticos son aquellos donde el código funciona aparentemente bien, pero produce resultados incorrectos o comportamientos inesperados que se manifiestan mucho después.

## Ejemplo con `CuentaSobregiro`

||||
|-|-|-|
El cliente (**verificarYRetirar()**) espera y funciona perfectamente con objetos de tipo `CuentaBancaria`.|Pero si pasamos un objeto de tipo `CuentaSobregiro` como cuenta origen **¡no "falla"!**.|El cliente se fía de la invariante definida en CuentaBancaria, pero la cuenta con sobregiro permite retirar más dinero del disponible. El resultado es que se pueden realizar múltiples transferencias que exceden el saldo, potencialmente causando problemas graves en un sistema financiero real.

```java
public class EjemploCliente {
    public static void main(String[] args) {

        CuentaBancaria cuenta = new CuentaBancaria();
        cuenta.depositar(100);

        System.out.println("> Retiro dentro del saldo");
        verificarYRetirar(cuenta, 50);

        System.out.println("> Retiro que excede el saldo");
        verificarYRetirar(cuenta, 150);

        System.out.println("> Estado final de la cuenta");
        System.out.println("Saldo cuenta normal: " + cuenta.consultarSaldo());
    }

    public static void verificarYRetirar(CuentaBancaria cuenta, double monto) {
        try {
            double retirado = cuenta.retirar(monto);
            System.out.println("Retiro exitoso: " + retirado);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

## La sutileza del problema

Lo que hace grave esta violación es que:

1. **El código se ejecuta sin excepciones**: No hay errores visibles inmediatos.
1. **El comportamiento parece correcto superficialmente**: La transferencia se realiza.
1. **Las consecuencias aparecen después**: Desbalances contables, saldos negativos inesperados, etc.
1. **Es difícil rastrear el origen**: El problema podría manifestarse en partes completamente diferentes del sistema.

## Casos reales

Este tipo de problemas se ha observado en sistemas reales:

- Sistemas contables que permiten registrar transacciones que deberían ser rechazadas
- Aplicaciones de seguridad donde las verificaciones son burladas silenciosamente
- Interfaces de usuario donde los elementos se comportan de forma inesperada
- Sistemas distribuidos con comportamientos inconsistentes entre nodos

## La verdadera lección de LSP

Barbara Liskov no solo estaba preocupada por los errores evidentes, sino por mantener la corrección semántica de los programas. Cuando se viola LSP, se rompe la sustitución semántica, no solo la sintáctica.

Como programadores, debemos ser especialmente cautelosos con las subclases que:

- Cambian invariantes silenciosamente.
- Debilitan postcondiciones sin evidencia clara.
- Introducen efectos secundarios no documentados.

Porque estos son los casos donde el código "no falla" - y ese es precisamente el problema.
