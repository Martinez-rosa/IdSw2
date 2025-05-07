package DOO.DD.DD02.v000mal;

public class CeldaLimpia extends Celda {
    
    public CeldaLimpia(int fila, int columna) {
        super(fila, columna);
    }
    
    @Override
    public String representacion() {
        return " . ";
    }
}