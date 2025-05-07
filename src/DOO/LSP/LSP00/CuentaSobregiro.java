package DOO.LSP.LSP00;

// Viola LSP modificando invariantes
class CuentaSobregiro extends CuentaBancaria {
    private double limiteCredito = 1000;
    private double tasaInteresPorSobregiro = 0.05; 

    // Viola LSP: altera invariante fundamental (el saldo puede ser negativo)
    @Override
    public double retirar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        
        if (monto <= saldo) {
            // Comportamiento normal si hay fondos
            this.saldo = saldo - monto;
        } 
        else if (monto <= saldo + limiteCredito) {
            // Permite sobregiro hasta el límite de crédito
            double sobregiro = monto - saldo;
            double intereses = sobregiro * tasaInteresPorSobregiro;
            this.saldo = -(sobregiro + intereses);
        }
        else {
            throw new IllegalArgumentException("Excede el límite de crédito disponible");
        }
        
        return monto; // Siempre devuelve lo solicitado
    }

    // También viola invariante mediante nuevo comportamiento
    public void aplicarInteresesSobregiro() {
        if (saldo < 0) {
            double interesesAdicionales = Math.abs(saldo) * tasaInteresPorSobregiro;
            saldo = saldo - interesesAdicionales;
        }
    }
}