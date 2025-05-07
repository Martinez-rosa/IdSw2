package DOO.LSP.LSP01;

public class PoliticaSobregiro implements PoliticaRetiro {
    private final double LIMITE_CREDITO = 500;
    private final double TASA_INTERES = 0.05;

    public double retirar(double saldo, double monto) {
        if (monto <= 0) throw new IllegalArgumentException("El monto debe ser positivo");

        double disponible = saldo + LIMITE_CREDITO;
        if (monto > disponible) throw new IllegalArgumentException("Excede el límite de crédito disponible");

        double nuevoSaldo = saldo - monto;
        if (nuevoSaldo < 0) {
            double intereses = Math.abs(nuevoSaldo) * TASA_INTERES;
            nuevoSaldo -= intereses;
        }
        return nuevoSaldo;
    }
}