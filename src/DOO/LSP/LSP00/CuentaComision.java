package DOO.LSP.LSP00;

// Viola LSP con postcondiciones más débiles
class CuentaComision extends CuentaBancaria {
    private final double COMISION_FIJA = 5.0;
    
    // Viola LSP: postcondición más débil (retorna menos dinero del solicitado)
    @Override
    public double retirar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        
        double montoTotal = monto + COMISION_FIJA;
        
        if (montoTotal > saldo) {
            throw new IllegalArgumentException("Fondos insuficientes para cubrir monto y comisión");
        }
        
        saldo = saldo - montoTotal;
        return monto; // Devuelve lo solicitado pero cobra comisión extra
        // Viola invariante: valorDevuelto != montoDeducido (es menor por la comisión)
    }
}