package DOO.DD.DD00.v000mal;

import java.util.Random;
import java.util.Scanner;

public class Restaurante {

	private Recepcionista recepcionista;

	private Restaurante() {
		recepcionista = new Recepcionista();
	}

	public static void main(String[] args) {
		new Restaurante().simularEscenario();
	}

	private void simularEscenario() {
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < 5; i++) {
			Persona persona = null;
			if (random.nextInt(2) == 0) {
				persona = new Hombre();
			} else {
				persona = new Mujer();
			}
			this.simularEscenario(persona);
			new Scanner(System.in).nextLine();
		}
	}

	private void simularEscenario(Persona persona) {
		persona.saludar();
		recepcionista.recibir(persona);
		persona.despedirse();
		recepcionista.agradecerVisita();
		System.out.println("... ");
		System.out.println("... pasa el tiempo!");
		System.out.println("... ");
	}
}
