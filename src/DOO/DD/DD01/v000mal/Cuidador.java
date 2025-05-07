package DOO.DD.DD01.v000mal;

public class Cuidador {
    public void alimentar(Animal animal) {
        System.out.println("Soy un cuidador que alimenta a los animales");
        
        // Comportamiento específico según el tipo
        if (animal.getTipo().equals("Pajaro")) {
            System.out.println("Le doy semillas especiales para aves");
            System.out.println("Le silbo una melodía que le gusta");
            System.out.println("El pájaro canta alegremente en respuesta");
        } else if (animal.getTipo().equals("Perro")) {
            System.out.println("Le doy croquetas premium");
            System.out.println("El perro mueve la cola felizmente");
        } else {
            System.out.println("Le doy comida genérica");
        }
        
        System.out.println("Limpio el espacio del animal");
    }
}