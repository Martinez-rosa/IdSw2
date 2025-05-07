public class Recepcionista {

	public void recibir(Persona persona) {
		System.out.println("!!!!!!!!!! Soy un recepcionista que se alegra de su visita");
		persona.aceptar(this);		
	}
	
	public void visitar(Mujer mujer) {
		System.out.println("!!!!!!!!!! Soy un recepcionista que se admira de su belleza");
		mujer.escucharHalago();	
		System.out.println("!!!!!!!!!! Soy un recepcionista que se admira de su existencia");
		mujer.escucharPiropo();
	}

	public void visitar(Hombre hombre) {
		hombre.recibirPalmada(this);
	}

	public void recibirPropina(int euros) {
		System.out.println("!!!!!!!!!! Soy un recepcionista que gané " + euros + " euros");
	}
	
	public void agradecerVisita() {
		System.out.println("!!!!!!!!!! Soy un recepcionista que agradece la visita");
	}

}
