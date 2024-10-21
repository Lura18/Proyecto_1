package proyecto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

	//Atributos
	public class ProgresoPath {
	private LearningPath lp;
    private Date fechaInicioPath;
    private Date fechaFinPath;
	private float porcentajePath;
    private float tasaExito;
    private float tasaFracaso;
    private List<Actividad> actividadesRealizadas;
  
    //Constructor
    public ProgresoPath(LearningPath lp, Date fechaInicioPath) {
		super();
		this.lp = lp;
		this.fechaInicioPath = fechaInicioPath;
		this.fechaFinPath = null;
		this.porcentajePath = 0;
		this.tasaExito = 0;
		this.tasaFracaso = 0;
		this.actividadesRealizadas = new ArrayList<Actividad>();
	}
    
    //Getter and setter
	public Date getFechaInicioPath() {
		return fechaInicioPath;
	}
	public Date getFechaFinPath() {
		return fechaFinPath;
	}
	public void setFechaFinPath(Date fechaFinPath) {
		this.fechaFinPath = fechaFinPath;
	}
	public float getPorcentajePath() {
		return porcentajePath;
	}
	public float getTasaExito() {
		return tasaExito;
	}
	public float getTasaFracaso() {
		return tasaFracaso;
	}

	public List<Actividad> getActividadesRealizadas() {
		return actividadesRealizadas;
	}

	public LearningPath getLp() {
		return lp;
	}

	
	//Métodos
	public void agregarActividadRealizada(Actividad actividad) {
		actividadesRealizadas.add(actividad);
	}
}