package proyecto;

import java.util.List;
import java.util.Scanner;

public class PreguntaMultiple {
	
	//Atributos
	private String textoPregunta;
    private List<String> opciones;
    private int opcionCorrecta;
    private List<String> explicaciones;
    
    //Cosntructor
	public PreguntaMultiple(String textoPregunta, List<String> opciones, int opcionCorrecta, List<String> explicaciones) {
        if (opciones.size() != 4 || explicaciones.size() != 4) {
        	System.out.println(opciones.size());
        	System.out.println(explicaciones.size());
            throw new IllegalArgumentException("Debe haber exactamente 4 opciones y 4 explicaciones.");
        }
		this.textoPregunta = textoPregunta;
		this.opciones = opciones;
		this.opcionCorrecta = opcionCorrecta;
		this.explicaciones = explicaciones;
	}
	
	//Get and set
	public String getTextoPregunta() {
		return textoPregunta;
	}
	public List<String> getOpciones() {
		return opciones;
	}
	public List<String> getExplicaciones() {
		return explicaciones;
	}
	
	//Metodos
    public boolean mostrarYResolver(Scanner scanner) {

		System.out.println(textoPregunta);
		
		for (int i = 0; i < opciones.size(); i++) {
		    System.out.println((i + 1) + ". " + opciones.get(i));
		}
		
        System.out.print("Elige una opción (1-4): ");
        int respuestaElegida = Integer.parseInt(scanner.nextLine());  // Ajustar a índice 0

        boolean esCorrecta = (respuestaElegida == opcionCorrecta);
        
        if (esCorrecta) {
            System.out.println("¡Correcto!");
        } else {
            System.out.println("Incorrecto.");
        }
        System.out.println(explicaciones.get(respuestaElegida));
        
		return esCorrecta;
        
    }
    
    
}
