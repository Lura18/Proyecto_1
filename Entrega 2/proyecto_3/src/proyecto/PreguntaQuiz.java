package proyecto;

import java.util.List;
import java.util.Scanner;

public abstract class PreguntaQuiz {

	protected String textoPregunta;
    protected int opcionCorrecta;
    protected List<String> explicaciones;
    
    
	public PreguntaQuiz(String textoPregunta, int opcionCorrecta, List<String> explicaciones) {
		this.textoPregunta = textoPregunta;
		this.opcionCorrecta = opcionCorrecta;
		this.explicaciones = explicaciones;
	}
	//Get and set


	public String getTextoPregunta() {
		return textoPregunta;
	}
	public int getOpcionCorrecta() {
		return opcionCorrecta;
	}
	public List<String> getExplicaciones() {
		return explicaciones;
	}
	
	
	//metodos
	public abstract boolean mostrarYResolver(Scanner scanner);
	
	}
