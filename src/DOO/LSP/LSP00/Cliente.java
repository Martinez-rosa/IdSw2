package DOO.LSP.LSP00;

public class Cliente {
    public static void main(String[] args) {
        probar("Cuenta Bancaria Normal", new CuentaBancaria());
        //probar("Cuenta Limitada", new CuentaLimitada());
        //probar("Cuenta Comisión", new CuentaComision());
        //probar("Cuenta Sobregiro", new CuentaSobregiro());
    }

    private static void probar(String nombre, CuentaBancaria cuenta) {
        System.out.println("== " + nombre + " ==");
        cuenta.depositar(1000);
        try {
            for (int i = 1; i <= 15; i++) {
                cuenta.retirar(100);
                System.out.println("Retiro #" + i + " | Saldo: " + cuenta.consultarSaldo());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println();
    }
}
