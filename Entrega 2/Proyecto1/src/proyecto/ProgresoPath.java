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
    public ProgresoPath(Date fechaInicioPath, Date fechaFinPath, float porcentajePath, float tasaExito, float tasaFracaso) {
		super();
		this.fechaInicioPath = fechaInicioPath;
		this.fechaFinPath = fechaFinPath;
		this.porcentajePath = porcentajePath;
		this.tasaExito = tasaExito;
		this.tasaFracaso = tasaFracaso;
		this.actividadesRealizadas = new ArrayList<Actividad>();
	}
    
    //Getter and setter
	public Date getFechaInicioPath() {
		return fechaInicioPath;
	}
	public void setFechaInicioPath(Date fechaInicioPath) {
		this.fechaInicioPath = fechaInicioPath;
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
	public void setPorcentajePath(float porcentajePath) {
		this.porcentajePath = porcentajePath;
	}
	public float getTasaExito() {
		return tasaExito;
	}
	public void setTasaExito(float tasaExito) {
		this.tasaExito = tasaExito;
	}
	public float getTasaFracaso() {
		return tasaFracaso;
	}
	public void setTasaFracaso(float tasaFracaso) {
		this.tasaFracaso = tasaFracaso;
	}

	public List<Actividad> getActividadesRealizadas() {
		return actividadesRealizadas;
	}

	public LearningPath getLp() {
		return lp;
	}

	
	//Métodos
	
}