package DOO.LSP.LSP01;

public class CuentaBancaria {
    private double saldo;
    private PoliticaRetiro politica;

    public CuentaBancaria(PoliticaRetiro politica) {
        this.politica = politica;
    }

    public void depositar(double monto) {
        if (monto <= 0) throw new IllegalArgumentException("El monto debe ser positivo");
        saldo += monto;
    }

    public double retirar(double monto) {
        saldo = politica.retirar(saldo, monto);
        return monto;
    }

    public double consultarSaldo() {
        return saldo;
    }
}