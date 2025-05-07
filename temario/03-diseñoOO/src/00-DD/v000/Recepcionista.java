public class Recepcionista {

	public void recibir(Persona persona) {
		System.out.println("CLASE_RECEPCIONISTA Soy un recpcionista que se alegra de su visita");
		if (persona instanceof Mujer) {
			System.out.println("CLASE_RECEPCIONISTA Soy un recpcionista que se admira de su belleza");
			((Mujer) persona).escucharHalago();	
			System.out.println("CLASE_RECEPCIONISTA Soy un recpcionista que se admira de su existencia");
			((Mujer) persona).escucharPiropo();	
		} else {
			((Hombre) persona).recibirPalmada(this);
		}	
	}

	public void recibirPropina(int euros) {
		System.out.println("CLASE_RECEPCIONISTA Soy un recpcionista que gané " + euros + " euros");
	}
	
	public void agradecerVisita() {
		System.out.println("CLASE_RECEPCIONISTA Soy un recpcionista que agradece la visita");
	}

}
