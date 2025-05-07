package DOO.DD.DD00.v003extendida;

public class Hombre extends Persona {

	public void recibirPalmada(Recepcionista recepcionista) {
		System.out.println("CLASE_HOMBRE Soy un hombre lleno de vanidad");		recepcionista.recibirPropina(3);
	}	
	
	@Override
	public void aceptar(VisitadorPersona visitadorPersona) {
		visitadorPersona.visitar(this);
	}
}
