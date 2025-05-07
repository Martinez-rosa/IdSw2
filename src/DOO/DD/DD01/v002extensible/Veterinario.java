package DOO.DD.DD01.v002extensible;

public class Veterinario implements VisitanteAnimal {
    @Override
    public void visitar(Perro perro) {
        System.out.println("Examino al perro");
        System.out.println("Verifico su pelaje y dientes");
        perro.ladrar();
    }
    
    @Override
    public void visitar(Pajaro pajaro) {
        System.out.println("Examino al pájaro");
        System.out.println("Verifico sus plumas y pico");
        pajaro.posarseEnMano();
    }
    
    public void examinar(Animal animal) {
        System.out.println("Soy un veterinario que examina a los animales");
        
        // Uso del patrón Visitor para el doble despacho
        animal.aceptar(this); // Segundo despacho
        
        System.out.println("Doy recomendaciones para su cuidado");
    }
}