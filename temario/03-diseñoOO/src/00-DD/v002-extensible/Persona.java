public abstract class Persona {

	public void saludar(){
		System.out.println("CLASE_PERSONA Soy una persona que saluda al entrar");
	}

	public abstract void aceptar(VisitadorPersona visitadorPersona);
	
	public void pedir() {
		System.out.println("CLASE_PERSONA Soy una persona que pide un menú");
	}
	
	public void despedirse() {
		System.out.println("CLASE_PERSONA Soy una persona que se despide al salir");
	}

}
