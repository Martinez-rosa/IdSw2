package DOO.DD.DD02.v001basico;

import java.util.Random;

public class Aspiradora {
    private Posicion posicion;
    private int bateria;
    private int capacidadBolsa;
    private int basuraRecogida;
    private Random random;
    private int pasos;
    
    public Aspiradora(int fila, int columna) {
        this.posicion = new Posicion(fila, columna);
        this.bateria = 100;
        this.capacidadBolsa = 50;
        this.basuraRecogida = 0;
        this.random = new Random();
        this.pasos = 0;
    }
    
    public Posicion getPosicion() {
        return posicion;
    }
    
    public int getBateria() {
        return bateria;
    }
    
    public int getBasuraRecogida() {
        return basuraRecogida;
    }
    
    public int getCapacidadBolsa() {
        return capacidadBolsa;
    }
    
    public int getPasos() {
        return pasos;
    }
    
    public void vaciarBolsa() {
        this.basuraRecogida = 0;
        System.out.println("Bolsa de basura vaciada.");
    }
    
    public void cargarBateria() {
        this.bateria = 100;
        System.out.println("Batería cargada al 100%.");
    }
    
    public void moverAleatorio(Mundo mundo) {
        if (bateria <= 0) {
            System.out.println("¡Sin batería! La aspiradora se ha detenido.");
            return;
        }
        
        if (basuraRecogida >= capacidadBolsa) {
            System.out.println("¡Bolsa llena! La aspiradora se ha detenido.");
            return;
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
                bateria--;
                pasos++;
                
                Celda celdaActual = mundo.getCelda(posicion.getFila(), posicion.getColumna());
                boolean cambio = celdaActual.aceptarAspiradora(this);
                
                if (cambio && celdaActual instanceof CeldaSucia) {
                    mundo.setCelda(posicion.getFila(), posicion.getColumna(), 
                                  new CeldaLimpia(posicion.getFila(), posicion.getColumna()));
                }
                
                return;
            }
        }
        
        System.out.println("La aspiradora está atrapada y no puede moverse.");
    }
    
    public boolean moverHaciaObjetivo(Mundo mundo, Posicion objetivo) {
        if (bateria <= 0) {
            System.out.println("¡Sin batería! La aspiradora se ha detenido.");
            return false;
        }
        
        if (basuraRecogida >= capacidadBolsa) {
            System.out.println("¡Bolsa llena! La aspiradora se ha detenido.");
            return false;
        }
        
        int deltaFila = objetivo.getFila() - posicion.getFila();
        int deltaColumna = objetivo.getColumna() - posicion.getColumna();
        
        int dirFila = deltaFila == 0 ? 0 : (deltaFila > 0 ? 1 : -1);
        int dirColumna = deltaColumna == 0 ? 0 : (deltaColumna > 0 ? 1 : -1);
        
        int nuevaFila = posicion.getFila() + dirFila;
        int nuevaColumna = posicion.getColumna() + dirColumna;
        
        if (mundo.posicionAccesible(nuevaFila, nuevaColumna)) {
            posicion.setFila(nuevaFila);
            posicion.setColumna(nuevaColumna);
            bateria--;
            pasos++;
            
            Celda celdaActual = mundo.getCelda(posicion.getFila(), posicion.getColumna());
            boolean cambio = celdaActual.aceptarAspiradora(this);
            
            if (cambio && celdaActual instanceof CeldaSucia) {
                mundo.setCelda(posicion.getFila(), posicion.getColumna(), 
                              new CeldaLimpia(posicion.getFila(), posicion.getColumna()));
            }
            
            return true;
        } else {
            moverAleatorio(mundo);
            return false;
        }
    }
    
    public void visitarCeldaLimpia(CeldaLimpia celda) {
    }
    
    public boolean visitarCeldaSucia(CeldaSucia celda) {
        int nivelAnterior = celda.getNivelSuciedad();
        boolean limpiada = celda.reducirSuciedad();
        basuraRecogida++;
        
        System.out.printf("Limpiado celda [%d,%d]. Nivel anterior: %d, Nivel actual: %s%n", 
                          celda.getFila(), celda.getColumna(), nivelAnterior,
                          limpiada ? "limpio" : celda.getNivelSuciedad());
        
        return limpiada;
    }
    
    public void visitarCeldaObstaculo(CeldaObstaculo celda) {
    }
}