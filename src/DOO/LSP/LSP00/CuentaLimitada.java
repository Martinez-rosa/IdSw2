package DOO.LSP.LSP00;

// Viola LSP con precondiciones más restrictivas
class CuentaLimitada extends CuentaBancaria {
    private int retirosRealizados = 0;
    private final int LIMITE_RETIROS = 3;

    // Viola LSP: precondición más restrictiva (requiere no haber excedido el límite de retiros)
    @Override
    public double retirar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        if (monto > saldo) {
            throw new IllegalArgumentException("Fondos insuficientes");
        }
        if (retirosRealizados >= LIMITE_RETIROS) {
            throw new IllegalArgumentException("Se ha excedido el límite de retiros");
        }
        
        this.saldo -= monto;
        retirosRealizados++;
        return monto;
    }
}