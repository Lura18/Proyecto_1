package proyecto;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PreguntaTrueFalse extends PreguntaQuiz {
	
	private List<String> opciones;

	public PreguntaTrueFalse(String textoPregunta, int opcionCorrecta, List<String> explicaciones) {
		super(textoPregunta, opcionCorrecta, explicaciones );
		// TODO Auto-generated constructor stub
		this.opciones = new ArrayList<String>();
		opciones.add("Verdadero");
		opciones.add("Falso");
	}

	@Override
	public boolean mostrarYResolver(Scanner scanner) {
		// TODO Auto-generated method stub
		System.out.println(textoPregunta);
		
		for (int i = 0; i < opciones.size(); i++) {
		    System.out.println((i + 1) + ". " + opciones.get(i));
		}
		
		boolean valida = false;
		int respuestaElegida = -1;
		
		while (!valida) {
	        System.out.print("Elige tu respuesta (1 o 2): ");
	        try {
	            respuestaElegida = Integer.parseInt(scanner.nextLine());

	            // Verificar si la opción está en el rango esperado (1-4)
	            if (respuestaElegida >= 1 && respuestaElegida <= 2) {
	                valida = true;
	            } else {
	                System.out.println("Opción no válida. Intenta nuevamente.");
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Entrada no válida. Por favor, ingresa un número (1 o 2).");
	        }
		}

        boolean esCorrecta = (respuestaElegida == opcionCorrecta);
        
        if (esCorrecta) {
            System.out.println("¡Correcto!");
        } else {
            System.out.println("Incorrecto.");
        }
        System.out.println(explicaciones.get(0));
        
		return esCorrecta;
	}

}
