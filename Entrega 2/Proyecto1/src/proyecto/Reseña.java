package proyecto;

public class Reseña {
	
	//Atributos
	private String texto;
    private float rating;
    
    //Constructor
	public Reseña(String texto, float rating) {
		super();
		this.texto = texto;
		this.rating = rating;
	}
	
	//Get and set
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
}
