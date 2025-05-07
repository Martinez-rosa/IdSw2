package DOO.LSP.LSP01;

public class PoliticaBasica implements PoliticaRetiro {
    public double retirar(double saldo, double monto) {
        if (monto <= 0)
            throw new IllegalArgumentException("El monto debe ser positivo");
        if (monto > saldo)
            throw new IllegalArgumentException("Fondos insuficientes");
        return saldo - monto;
    }
}