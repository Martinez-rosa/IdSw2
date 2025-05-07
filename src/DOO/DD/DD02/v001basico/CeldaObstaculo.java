package DOO.DD.DD02.v001basico;

public class CeldaObstaculo extends Celda {
    private String tipo;
    
    public CeldaObstaculo(int fila, int columna, String tipo) {
        super(fila, columna);
        this.tipo = tipo;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    @Override
    public String representacion() {
        if ("sofa".equals(tipo)) {
            return "[##]";
        } else if ("pared".equals(tipo)) {
            return "####";
        } else {
            return "[--]"; // Obstáculo genérico
        }
    }
    
    @Override
    public boolean aceptarAspiradora(Aspiradora aspiradora) {
        aspiradora.visitarCeldaObstaculo(this);
        return false; // La celda no cambia
    }
    
    @Override
    public boolean aceptarGato(Gato gato) {
        gato.visitarCeldaObstaculo(this);
        return false; // La celda no cambia
    }
}