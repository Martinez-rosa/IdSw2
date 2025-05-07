package DOO.DD.DD01.v001basico;

public class Perro extends Animal {
    private Extremidades[] patas;
    private Cola cola;
    
    public Perro() {
        super();
        this.patas = new Extremidades[4];
        this.cola = new Cola();
    }
    
    public void ladrar() {
        System.out.println("¡Guau, guau!");
    }
    
    public void moverCola() {
        System.out.println("El perro mueve la cola felizmente");
    }
    
    @Override
    public void moverse() {
        System.out.println("El perro corre en cuatro patas");
    }
    
    @Override
    public void comunicarse() {
        System.out.println("El perro ladra: ¡Guau, guau!");
    }
    
    // Implementación del método aceptar para el doble despacho
    @Override
    public void aceptar(Cuidador cuidador) {
        cuidador.visitar(this); // Primer despacho
    }
}

class Extremidades {
    // Representación simplificada
}

class Cola {
    // Representación simplificada
}