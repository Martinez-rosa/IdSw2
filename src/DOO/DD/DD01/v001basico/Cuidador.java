package DOO.DD.DD01.v001basico;

public class Cuidador {
    public void alimentar(Animal animal) {
        System.out.println("Soy un cuidador que alimenta a los animales");
        
        // Uso del doble despacho - el animal determinará qué método visitar se llamará
        animal.aceptar(this); // Segundo despacho
        
        System.out.println("Limpio el espacio del animal");
    }
    
    // Métodos sobrecargados para cada tipo de animal
    public void visitar(Perro perro) {
        System.out.println("Le doy croquetas premium al perro");
        perro.moverCola();
    }
    
    public void visitar(Pajaro pajaro) {
        System.out.println("Le doy semillas especiales para aves");
        System.out.println("Le silbo una melodía que le gusta");
        pajaro.cantar();
    }
}