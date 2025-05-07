package DOO.DD.DD02.v002extensible;

public class CeldaLimpia extends Celda {
    
    public CeldaLimpia(int fila, int columna) {
        super(fila, columna);
    }
    
    @Override
    public String representacion() {
        return " . ";
    }
    
    @Override
    public boolean aceptar(VisitanteCelda visitante) {
        return visitante.visitarCeldaLimpia(this);
    }
}