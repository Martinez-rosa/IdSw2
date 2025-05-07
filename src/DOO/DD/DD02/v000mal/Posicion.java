package DOO.DD.DD02.v000mal;

public class Posicion {
    private int fila;
    private int columna;
    
    public Posicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }
    
    public int getFila() {
        return fila;
    }
    
    public int getColumna() {
        return columna;
    }
    
    public void setFila(int fila) {
        this.fila = fila;
    }
    
    public void setColumna(int columna) {
        this.columna = columna;
    }
    
    public void mover(int direccion) {
        switch (direccion) {
            case 0: // arriba
                fila--;
                break;
            case 1: // arriba-derecha
                fila--;
                columna++;
                break;
            case 2: // derecha
                columna++;
                break;
            case 3: // abajo-derecha
                fila++;
                columna++;
                break;
            case 4: // abajo
                fila++;
                break;
            case 5: // abajo-izquierda
                fila++;
                columna--;
                break;
            case 6: // izquierda
                columna--;
                break;
            case 7: // arriba-izquierda
                fila--;
                columna--;
                break;
        }
    }
}