package DOO.LSP.LSP00;

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