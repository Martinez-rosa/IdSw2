package DOO.DD.DD02.v000mal;

import java.util.Random;

public class Gato {
    private Posicion posicion;
    private int pasosRestantes;
    private Random random;
    
    public Gato(int fila, int columna, int pasos) {
        this.posicion = new Posicion(fila, columna);
        this.pasosRestantes = pasos;
        this.random = new Random();
    }
    
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
                ensuciar(mundo);
                return true;
            }
        }
        
        pasosRestantes--;
        return pasosRestantes > 0;
    }
    
    public void ensuciar(Mundo mundo) {
        Celda celdaActual = mundo.getCelda(posicion.getFila(), posicion.getColumna());
        
        if (celdaActual instanceof CeldaLimpia) {
            mundo.setCelda(posicion.getFila(), posicion.getColumna(), 
                           new CeldaSucia(posicion.getFila(), posicion.getColumna(), 1));
            System.out.printf("Gato ensució celda limpia [%d,%d]%n", 
                               posicion.getFila(), posicion.getColumna());
        } 
        else if (celdaActual instanceof CeldaSucia) {
            CeldaSucia celdaSucia = (CeldaSucia) celdaActual;
            int nivelAnterior = celdaSucia.getNivelSuciedad();
            celdaSucia.ensuciar();
            System.out.printf("Gato aumentó suciedad en celda [%d,%d]. Nivel anterior: %d, Nivel actual: %d%n", 
                               posicion.getFila(), posicion.getColumna(), nivelAnterior, celdaSucia.getNivelSuciedad());
        }
    }
}