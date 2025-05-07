package DOO.DD.DD02.v000mal;

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
            return "[#]";
        } else if ("pared".equals(tipo)) {
            return "###";
        } else {
            return "[-]"; // Obstáculo genérico
        }
    }
}