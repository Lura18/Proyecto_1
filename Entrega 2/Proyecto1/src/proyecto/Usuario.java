package proyecto;

public abstract class Usuario {
	
	//Atributos
	protected String nombre;
	protected String correo;
	protected String contrasena;
	
	//Constructor
    public Usuario(String nombre, String correo, String contrasena) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
    }
    
	//Get y set
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	//Metodos
	public abstract void verLearningPaths();
	
	public void darReseñaActividad(Actividad actividad, String texto, float rating){
		if (rating < 0 || rating > 10) {
	        System.out.println("El rating debe estar entre 0 y 10.");
	    }
	    Reseña reseña = new Reseña(texto, rating);
	    actividad.agregarReseña(reseña);
	    System.out.println("Reseña agregada con éxito. Gracias por ayudarnos a mejorar!");
	}
	
	
}
