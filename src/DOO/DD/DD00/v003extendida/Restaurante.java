package DOO.DD.DD00.v003extendida;

import java.util.Random;
import java.util.Scanner;

public class Restaurante {

	private Recepcionista recepcionista;

	private Camarero camarero;

	private Restaurante() {
		recepcionista = new Recepcionista();
		camarero = new Camarero();
	}

	public static void main(String[] args) {
		new Restaurante().simularEscenario();
	}

	private void simularEscenario() {
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < 6; i++) {
			Persona persona = null;
			int tipo = random.nextInt(3);
			if (tipo == 0) {
				persona = new Hombre();
			} else if (tipo == 1) {
				persona = new Mujer();
			} else {
				persona = new Androide();
			}
			this.simularEscenario(persona);
			new Scanner(System.in).nextLine();
		}
	}

	private void simularEscenario(Persona persona) {
		persona.saludar();
		recepcionista.recibir(persona);
		persona.pedir();
		camarero.servir(persona);
		persona.despedirse();
		camarero.recoger();
		recepcionista.agradecerVisita();
		System.out.println("... ");
		System.out.println("... pasa el tiempo!");
		System.out.println("... ");
	}

}
