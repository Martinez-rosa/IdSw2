package DOO.DD.DD00.v003malExtendida;

public class Recepcionista {

	public void recibir(Persona persona) {
		System.out.println("CLASE_RECEPCIONISTA Soy un recepcionista que se alegra de su visita");
		
		// Usando instanceof para identificar el tipo de persona
		if (persona instanceof Mujer) {
			System.out.println("CLASE_RECEPCIONISTA Soy un recepcionista que se admira de su belleza");
			((Mujer) persona).escucharHalago();
			System.out.println("CLASE_RECEPCIONISTA Soy un recepcionista que se admira de su existencia");
			((Mujer) persona).escucharPiropo();
		} else if (persona instanceof Androide) {
			// Comportamiento específico para androides
			System.out.println("CLASE_RECEPCIONISTA Soy un recepcionista sorprendido por la tecnología del androide");
			((Androide) persona).mostrarTecnologia();
		} else {
			// Por defecto, asumimos que es un hombre
			((Hombre) persona).recibirPalmada(this);
		}
	}

	public void recibirPropina(int euros) {
		System.out.println("CLASE_RECEPCIONISTA Soy un recepcionista que gané " + euros + " euros");
	}

	public void agradecerVisita() {
		System.out.println("CLASE_RECEPCIONISTA Soy un recepcionista que agradece la visita");
	}
}