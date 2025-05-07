package DOO.DD.DD02.v001basico;

public class CeldaLimpia extends Celda {
    
    public CeldaLimpia(int fila, int columna) {
        super(fila, columna);
    }
    
    @Override
    public String representacion() {
        return " . ";
    }
    
    @Override
    public boolean aceptarAspiradora(Aspiradora aspiradora) {
        aspiradora.visitarCeldaLimpia(this);
        return false; // La celda no cambia
    }
    
    @Override
    public boolean aceptarGato(Gato gato) {
        // El gato ensucia la celda limpia
        return gato.visitarCeldaLimpia(this);
    }
}