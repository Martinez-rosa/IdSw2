package DOO.LSP.LSP00;

class CuentaBancaria {
    protected double saldo;
    
    // Precondición: el monto debe ser positivo
    public void depositar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        this.saldo = saldo + monto;
    }
    
    // Precondición : el monto debe ser positivo y no debe exceder el saldo
    // Postcondición: el saldo se reduce exactamente por el monto retirado 
    //                y retorna exactamente el monto solicitado
    public double retirar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        if (monto > saldo) {
            throw new IllegalArgumentException("Fondos insuficientes");
        }
        this.saldo = saldo - monto;
        return monto; // Garantiza devolver exactamente el monto solicitado
    }
    
    public double consultarSaldo() {
        return saldo;
    }
}
