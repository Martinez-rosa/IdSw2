package DOO.LSP.LSP01;

public class PoliticaConComision implements PoliticaRetiro {
    private final double COMISION = 5.0;

    public double retirar(double saldo, double monto) {
        double total = monto + COMISION;
        if (monto <= 0) throw new IllegalArgumentException("El monto debe ser positivo");
        if (total > saldo) throw new IllegalArgumentException("Fondos insuficientes para cubrir comisión");
        return saldo - total;
    }
}