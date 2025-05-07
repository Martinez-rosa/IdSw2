package DOO.DD.DD00.v001basico;

public abstract class Persona {

	public void saludar(){
		System.out.println("CLASE_PERSONA Soy una persona que saluda al entrar");
	}

	public abstract void aceptar(Recepcionista recepcionista);
	
	public void despedirse() {
		System.out.println("CLASE_PERSONA Soy una persona que se despide al salir");
	}

}
