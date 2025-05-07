package DOO.DD.DD00.v003extendida;

public class Recepcionista implements VisitadorPersona{

	@Override
	public void visitar(Androide androide) {
		System.out.println("CLASE_RECEPCIONISTA Soy un recepcionista sorprendido por la tecnología del androide");
		androide.mostrarTecnologia();
	}

	public void recibir(Persona persona) {
		System.out.println("CLASE_RECEPCIONISTA Soy un recepcionista que se alegra de su visita");
		persona.aceptar(this);		
	}
	
	@Override
	public void visitar(Mujer mujer) {
		System.out.println("CLASE_RECEPCIONISTA Soy un recepcionista que se admira de su belleza");
		mujer.escucharHalago();	
		System.out.println("CLASE_RECEPCIONISTA Soy un recepcionista que se admira de su existencia");
		mujer.escucharPiropo();
	}

	@Override
	public void visitar(Hombre hombre) {
		hombre.recibirPalmada(this);
	}

	public void recibirPropina(int euros) {
		System.out.println("CLASE_RECEPCIONISTA Soy un recepcionista que gané " + euros + " euros");
	}
	
	public void agradecerVisita() {
		System.out.println("CLASE_RECEPCIONISTA Soy un recepcionista que agradece la visita");
	}

}
