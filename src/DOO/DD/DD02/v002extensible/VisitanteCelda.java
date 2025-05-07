package DOO.DD.DD02.v002extensible;

public interface VisitanteCelda {
    boolean visitarCeldaLimpia(CeldaLimpia celda);
    
    boolean visitarCeldaSucia(CeldaSucia celda);
    
    boolean visitarCeldaObstaculo(CeldaObstaculo celda);
}