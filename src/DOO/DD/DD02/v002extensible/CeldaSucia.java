package DOO.DD.DD02.v002extensible;

public class CeldaSucia extends Celda {
    private int nivelSuciedad;
    
    public CeldaSucia(int fila, int columna, int nivelSuciedad) {
        super(fila, columna);
        this.nivelSuciedad = Math.min(4, Math.max(1, nivelSuciedad)); // Limitado entre 1 y 4
    }
    
    public int getNivelSuciedad() {
        return nivelSuciedad;
    }
    
    public boolean reducirSuciedad() {
        nivelSuciedad--;
        return nivelSuciedad <= 0;
    }
    
    public void aumentarSuciedad() {
        nivelSuciedad = Math.min(4, nivelSuciedad + 1);
    }
    
    @Override
    public String representacion() {
        switch (nivelSuciedad) {
            case 1: return "...";
            case 2: return "ooo";
            case 3: return "OOO";
            case 4: return "***";
            default: return " . "; // No debería ocurrir
        }
    }
    
    @Override
    public boolean aceptar(VisitanteCelda visitante) {
        return visitante.visitarCeldaSucia(this);
    }
}