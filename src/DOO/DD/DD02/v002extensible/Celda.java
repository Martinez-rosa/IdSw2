package DOO.DD.DD02.v002extensible;

public abstract class Celda {
    protected int fila;
    protected int columna;
    
    public Celda(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }
    
    public int getFila() {
        return fila;
    }
    
    public int getColumna() {
        return columna;
    }
    
    public abstract String representacion();
    
    public abstract boolean aceptar(VisitanteCelda visitante);
}