package DOO.DD.DD00.v001basico;

public class Mujer extends Persona {

	public void escucharHalago() {
		System.out.println("CLASE_MUJER Soy una mujer ruborizada");
	}

	public void escucharPiropo() {
		System.out.println("CLASE_MUJER Soy una mujer más ruborizada");
	}	
	
	@Override
	public void aceptar(Recepcionista recepcionista) {
		recepcionista.visitar(this);
	}

}
