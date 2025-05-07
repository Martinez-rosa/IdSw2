package DOO.DD.DD00.v003extendida;

public class Androide extends Persona {
	
	private int nivelBateria;
	
	public Androide() {
		nivelBateria = 50; // Nivel inicial de batería al 50%
	}
	
	public void recargar() {
		nivelBateria = 100;
		System.out.println("CLASE_ANDROIDE Soy un androide recargado al " + nivelBateria + "%");
	}
	
	public void mostrarTecnologia() {
		System.out.println("CLASE_ANDROIDE Soy un androide con IA avanzada, sensores cuánticos y piel sintética");
		System.out.println("CLASE_ANDROIDE Mi nivel de batería actual es " + nivelBateria + "%");
	}
	
	@Override
	public void saludar() {
		System.out.println("CLASE_ANDROIDE Soy un androide que saluda con precisión mecánica");
	}
	
	@Override
	public void pedir() {
		System.out.println("CLASE_ANDROIDE Soy un androide que no necesita comida, pero pido aceite de primera calidad");
	}
	
	@Override
	public void despedirse() {
		System.out.println("CLASE_ANDROIDE Soy un androide que se despide y entra en modo de suspensión");
		nivelBateria = nivelBateria - 10; // Gastar algo de batería
	}
	
	@Override
	public void aceptar(VisitadorPersona visitadorPersona) {
		visitadorPersona.visitar(this);
	}
}