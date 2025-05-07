package DOO.DD.DD02.v002extensible;

import java.util.Random;

public class Gato implements Entidad, VisitanteCelda {
    private Posicion posicion;
    private int pasosRestantes;
    private Random random;
    
    public Gato(int fila, int columna, int pasos) {
        this.posicion = new Posicion(fila, columna);
        this.pasosRestantes = pasos;
        this.random = new Random();
    }
    
    @Override
    public Posicion getPosicion() {
        return posicion;
    }
    
    public int getPasosRestantes() {
        return pasosRestantes;
    }
    
    public boolean moverYEnsuciar(Mundo mundo) {
        if (pasosRestantes <= 0) {
            return false; // El gato desaparece
        }
        
        for (int intentos = 0; intentos < 8; intentos++) {
            int direccion = random.nextInt(8);
            int nuevaFila = posicion.getFila();
            int nuevaColumna = posicion.getColumna();
            
            switch (direccion) {
                case 0: nuevaFila--; break;
                case 1: nuevaFila--; nuevaColumna++; break;
                case 2: nuevaColumna++; break;
                case 3: nuevaFila++; nuevaColumna++; break;
                case 4: nuevaFila++; break;
                case 5: nuevaFila++; nuevaColumna--; break;
                case 6: nuevaColumna--; break;
                case 7: nuevaFila--; nuevaColumna--; break;
            }
            
            if (mundo.posicionAccesible(nuevaFila, nuevaColumna)) {
                posicion.setFila(nuevaFila);
                posicion.setColumna(nuevaColumna);
                pasosRestantes--;
                
                Celda celdaActual = mundo.getCelda(posicion.getFila(), posicion.getColumna());
                boolean cambio = celdaActual.aceptar(this);
                
                if (cambio && celdaActual instanceof CeldaLimpia) {
                    mundo.setCelda(posicion.getFila(), posicion.getColumna(), 
                                   new CeldaSucia(posicion.getFila(), posicion.getColumna(), 1));
                }
                
                return true;
            }
        }
        
        pasosRestantes--;
        return pasosRestantes > 0;
    }
    
    
    @Override
    public boolean visitarCeldaLimpia(CeldaLimpia celda) {
        System.out.printf("Gato ensució celda limpia [%d,%d]%n", 
                          celda.getFila(), celda.getColumna());
        return true; // La celda debe convertirse en sucia
    }
    
    @Override
    public boolean visitarCeldaSucia(CeldaSucia celda) {
        int nivelAnterior = celda.getNivelSuciedad();
        celda.aumentarSuciedad();
        System.out.printf("Gato aumentó suciedad en celda [%d,%d]. Nivel anterior: %d, Nivel actual: %d%n", 
                          celda.getFila(), celda.getColumna(), nivelAnterior, celda.getNivelSuciedad());
        return false; // La celda sigue siendo sucia
    }
    
    @Override
    public boolean visitarCeldaObstaculo(CeldaObstaculo celda) {
        return false; // La celda no cambia
    }
}