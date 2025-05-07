package DOO.DD.DD01.v002extensible;

public abstract class Animal {
    protected Cabeza cabeza;
    protected Cuerpo cuerpo;
    
    public Animal() {
        this.cabeza = new Cabeza();
        this.cuerpo = new Cuerpo();
    }
    
    // Métodos comunes para todos los animales
    public void respirar() {
        System.out.println("El animal respira");
    }
    
    public void dormir() {
        System.out.println("El animal duerme");
    }
    
    // Métodos abstractos para comportamiento específicos
    public abstract void moverse();
    public abstract void comunicarse();
    
    // Método abstracto para implementar el doble despacho
    public abstract void aceptar(VisitanteAnimal visitante);
}

class Cabeza {
    // Representación simplificada
}

class Cuerpo {
    // Representación simplificada
}