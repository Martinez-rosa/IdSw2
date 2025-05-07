package DOO.LSP.LSP01;

public class PoliticaConLimite implements PoliticaRetiro {
    private int retirosRealizados = 0;
    private final int LIMITE = 3;

    public double retirar(double saldo, double monto) {
        if (retirosRealizados >= LIMITE)
            throw new IllegalArgumentException("Límite de retiros alcanzado");
        if (monto <= 0) throw new IllegalArgumentException("El monto debe ser positivo");
        if (monto > saldo) throw new IllegalArgumentException("Fondos insuficientes");
        retirosRealizados++;
        return saldo - monto;
    }
}