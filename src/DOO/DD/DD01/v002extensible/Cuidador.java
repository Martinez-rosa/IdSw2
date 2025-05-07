package DOO.DD.DD01.v002extensible;

public class Cuidador implements VisitanteAnimal {
    @Override
    public void visitar(Perro perro) {
        System.out.println("Le doy croquetas premium al perro");
        perro.moverCola();
    }
    
    @Override
    public void visitar(Pajaro pajaro) {
        System.out.println("Le doy semillas especiales para aves");
        System.out.println("Le silbo una melodía que le gusta");
        pajaro.cantar();
    }
    
    public void cuidar(Animal animal) {
        System.out.println("Soy un cuidador que alimenta a los animales");
        
        // Uso del patrón Visitor para el doble despacho
        animal.aceptar(this); // Segundo despacho
        
        System.out.println("Limpio el espacio del animal");
    }
}