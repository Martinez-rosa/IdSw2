package DOO.DD.DD01.v002extensible;

public class Pajaro extends Animal {
    private Alas alas;
    private Cola cola;
    
    public Pajaro() {
        super();
        this.alas = new Alas();
        this.cola = new Cola();
    }
    
    public void cantar() {
        System.out.println("¡Pío, pío!");
    }
    
    public void posarseEnMano() {
        System.out.println("El pájaro se posa en la mano");
    }
    
    @Override
    public void moverse() {
        System.out.println("El pájaro vuela batiendo sus alas");
    }
    
    @Override
    public void comunicarse() {
        System.out.println("El pájaro canta: ¡Pío, pío!");
    }
    
    // Implementación del método aceptar para el patrón Visitor
    @Override
    public void aceptar(VisitanteAnimal visitante) {
        visitante.visitar(this); // Primer despacho
    }
}

class Alas {
    // Representación simplificada
}

// Reutilizamos la clase Cola definida en Perro