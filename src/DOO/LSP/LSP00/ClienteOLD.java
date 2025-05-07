package DOO.LSP.LSP00;

public class ClienteOLD {
    public static void main(String[] args) {
        System.out.println("Test con Cuenta Bancaria Normal");
        CuentaBancaria normal = new CuentaBancaria();
        normal.depositar(1000);
        procesarRetiros(normal, 5); // Funciona para 5 retiros
        
        System.out.println("Test con Cuenta Limitada (Precondición más restrictiva)");
        CuentaBancaria limitada = new CuentaLimitada();
        limitada.depositar(1000);
        procesarRetiros(limitada, 5); // Fallará al 4to retiro
        
        System.out.println("Test con Cuenta Comisión (Postcondición más débil)");
        CuentaBancaria comision = new CuentaComision();
        comision.depositar(1000);
        double saldoEsperado = 1000 - (100 * 3); // 700
        realizarRetiros(comision, 3);
        // El saldo real será menor debido a las comisiones
        System.out.println("Saldo esperado si no hubiera comisiones: " + saldoEsperado);
        System.out.println("Saldo real (con comisiones): " + comision.consultarSaldo());
        
        System.out.println("Test con Cuenta Sobregiro (Invariante modificada)");
        CuentaBancaria sobregiro = new CuentaSobregiro();
        sobregiro.depositar(500);
        probarLimites(sobregiro);
    }
    
    // Método que espera el comportamiento de CuentaBancaria
    private static void procesarRetiros(CuentaBancaria cuenta, int cantidadRetiros) {
        try {
            for (int i = 0; i < cantidadRetiros; i++) {
                System.out.println("Retiro #" + (i+1) + ": " + cuenta.retirar(100));
                System.out.println("Saldo actual: " + cuenta.consultarSaldo());
            }
            System.out.println("Todos los retiros procesados exitosamente");
        } catch (Exception e) {
            System.out.println(">>>> Error en retiro: " + e.getMessage());
        }
    }
    
    // Método que ayuda a demostrar el problema de postcondición
    private static void realizarRetiros(CuentaBancaria cuenta, int cantidadRetiros) {
        try {
            for (int i = 0; i < cantidadRetiros; i++) {
                double montoRetirado = cuenta.retirar(100);
                System.out.println("Retiro #" + (i+1) + ": solicitado 100, recibido " + montoRetirado);
            }
        } catch (Exception e) {
            System.out.println(">>>> Error: " + e.getMessage());
        }
    }
    
    // Método para probar los límites y sobregiros
    private static void probarLimites(CuentaBancaria cuenta) {
        try {
            System.out.println("Saldo inicial: " + cuenta.consultarSaldo());
            System.out.println("Retirando 400...");
            cuenta.retirar(400);
            System.out.println("Saldo después del primer retiro: " + cuenta.consultarSaldo());
            System.out.println("Retirando 200 más (debería entrar en sobregiro)...");
            cuenta.retirar(200);
            System.out.println("Saldo final (debería ser negativo): " + cuenta.consultarSaldo());
            
            // Verificación de invariante rota
            if (cuenta.consultarSaldo() < 0) {
                System.out.println("INVARIANTE VIOLADA: El saldo es negativo, lo que no sería posible en CuentaBancaria normal");
            }
        } catch (Exception e) {
            System.out.println(">>>> Error: " + e.getMessage());
        }
    }
}