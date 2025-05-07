package DOO.DD.DD01.v000mal;

public class Animal {
    private Cabeza cabeza;
    private Cuerpo cuerpo;
    private String tipo; // "Perro" o "Pajaro"
    
    public Animal(String tipo) {
        this.tipo = tipo;
        this.cabeza = new Cabeza();
        this.cuerpo = new Cuerpo();
    }
    
    public void moverse() {
        if (tipo.equals("Perro")) {
            System.out.println("El perro corre en cuatro patas");
        } else if (tipo.equals("Pajaro")) {
            System.out.println("El pájaro vuela batiendo sus alas");
        } else {
            System.out.println("El animal se mueve");
        }
    }
    
    public void comunicarse() {
        if (tipo.equals("Perro")) {
            System.out.println("El perro ladra: ¡Guau, guau!");
        } else if (tipo.equals("Pajaro")) {
            System.out.println("El pájaro canta: ¡Pío, pío!");
        } else {
            System.out.println("El animal hace un sonido");
        }
    }
    
    public void comer() {
        if (tipo.equals("Perro")) {
            System.out.println("El perro come croquetas");
        } else if (tipo.equals("Pajaro")) {
            System.out.println("El pájaro come alpiste");
        } else {
            System.out.println("El animal come algo");
        }
    }
    
    public String getTipo() {
        return tipo;
    }
}

class Cabeza {
    // Representación simplificada
}

class Cuerpo {
    // Representación simplificada
}