package DOO.LSP.LSP01;

public class Cliente {
    public static void main(String[] args) {
        probarCuenta("Cuenta básica", new PoliticaBasica());
        //probarCuenta("Cuenta limitada", new PoliticaConLimite());
        //probarCuenta("Cuenta con comisión", new PoliticaConComision());
        //probarCuenta("Cuenta con sobregiro", new PoliticaSobregiro());
    }

    private static void probarCuenta(String nombre, PoliticaRetiro politica) {
        System.out.println("== " + nombre + " ==");
        CuentaBancaria cuenta = new CuentaBancaria(politica);
        cuenta.depositar(1000);
        try {
            for (int i = 1; i <= 5; i++) {
                cuenta.retirar(100);
                System.out.println("Retiro #" + i + " | Saldo: " + cuenta.consultarSaldo());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println();
    }
}