package DOO.DD.DD00.v003malExtendida;

public class Camarero {

	public void servir(Persona persona) {
		// Usando instanceof para identificar el tipo de persona
		if (persona instanceof Mujer) {
			System.out.println("CLASE_CAMARERO Soy un camarero que invita a un coktail");
			System.out.println("CLASE_CAMARERO Soy un camarero que entrega la comida");
		} else if (persona instanceof Androide) {
			// Comportamiento específico para androides
			System.out.println("CLASE_CAMARERO Soy un camarero que enchufa al androide para que se recargue");
			((Androide) persona).recargar();
			System.out.println("CLASE_CAMARERO Soy un camarero que entrega la comida");
		} else {
			// Por defecto, asumimos que es un hombre
			System.out.println("CLASE_CAMARERO Soy un camarero que entrega la comida");
			System.out.println("CLASE_CAMARERO Soy un camarero que invita a un coñac");
		}
	}
	
	public void recoger() {
		System.out.println("CLASE_CAMARERO Soy un camarero que recoge la mesa");
	}
}