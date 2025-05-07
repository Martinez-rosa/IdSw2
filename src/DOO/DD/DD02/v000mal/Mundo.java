package DOO.DD.DD02.v000mal;

import java.util.Random;

public class Mundo {
    private Celda[][] celdas;
    public int filas;
    public int columnas;
    private Random random;
    
    public Mundo(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.celdas = new Celda[filas][columnas];
        this.random = new Random();
        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new CeldaLimpia(i, j);
            }
        }
    }
    
    public void generarSuciedad(int porcentajeSucio) {
        int totalCeldas = filas * columnas;
        int celdasSucias = (int) (totalCeldas * porcentajeSucio / 100.0);
        
        for (int i = 0; i < celdasSucias; i++) {
            int fila = random.nextInt(filas);
            int columna = random.nextInt(columnas);
            int nivelSuciedad = random.nextInt(4) + 1; // Nivel 1-4
            
            if (!(celdas[fila][columna] instanceof CeldaObstaculo)) {
                celdas[fila][columna] = new CeldaSucia(fila, columna, nivelSuciedad);
            }
        }
    }
    
    public void generarObstaculos(int porcentajeObstaculos) {
        int totalCeldas = filas * columnas;
        int celdasObstaculo = (int) (totalCeldas * porcentajeObstaculos / 100.0);
        
        for (int i = 0; i < celdasObstaculo; i++) {
            int fila = random.nextInt(filas);
            int columna = random.nextInt(columnas);
            String tipoObstaculo = random.nextBoolean() ? "sofa" : "pared";
            
            celdas[fila][columna] = new CeldaObstaculo(fila, columna, tipoObstaculo);
        }
    }
    
    public Celda getCelda(int fila, int columna) {
        if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas) {
            return null;
        }
        return celdas[fila][columna];
    }
    
    public void setCelda(int fila, int columna, Celda celda) {
        if (fila >= 0 && fila < filas && columna >= 0 && columna < columnas) {
            celdas[fila][columna] = celda;
        }
    }
    
    public boolean posicionValida(int fila, int columna) {
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
    }
    
    public boolean posicionAccesible(int fila, int columna) {
        return posicionValida(fila, columna) && !(celdas[fila][columna] instanceof CeldaObstaculo);
    }
    
    public int calcularSuciedadTotal() {
        int suciedadTotal = 0;
        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (celdas[i][j] instanceof CeldaSucia) {
                    suciedadTotal += ((CeldaSucia) celdas[i][j]).getNivelSuciedad();
                }
            }
        }
        
        return suciedadTotal;
    }
    
    public Posicion buscarPosicionMasSucia() {
        int maxSuciedad = 0;
        Posicion posicionMasSucia = null;
        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (celdas[i][j] instanceof CeldaSucia) {
                    int nivelSuciedad = ((CeldaSucia) celdas[i][j]).getNivelSuciedad();
                    if (nivelSuciedad > maxSuciedad) {
                        maxSuciedad = nivelSuciedad;
                        posicionMasSucia = new Posicion(i, j);
                    }
                }
            }
        }
        
        return posicionMasSucia;
    }
    
    public void imprimir(Aspiradora aspiradora, Gato gato) {
        System.out.println("+--" + "-".repeat(columnas * 3) + "--+");
        
        for (int i = 0; i < filas; i++) {
            System.out.print("| ");
            
            for (int j = 0; j < columnas; j++) {
                if (aspiradora != null && aspiradora.getPosicion().getFila() == i && aspiradora.getPosicion().getColumna() == j) {
                    System.out.print("(O)");
                } 
                else if (gato != null && gato.getPosicion().getFila() == i && gato.getPosicion().getColumna() == j) {
                    System.out.print("\"^\"");
                } 
                else {
                    System.out.print(celdas[i][j].representacion());
                }
            }
            
            System.out.println(" |");
        }
        
        System.out.println("+--" + "-".repeat(columnas * 3) + "--+");
    }
}