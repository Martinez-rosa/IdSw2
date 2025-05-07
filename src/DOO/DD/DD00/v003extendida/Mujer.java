package DOO.DD.DD00.v003extendida;

public class Mujer extends Persona {

	public void escucharHalago() {
		System.out.println("CLASE_MUJER Soy una mujer ruborizada");
	}

	public void escucharPiropo() {
		System.out.println("CLASE_MUJER Soy una mujer más ruborizada");
	}	
	
	@Override
	public void aceptar(VisitadorPersona visitadorPersona) {
		visitadorPersona.visitar(this);
	}

}
