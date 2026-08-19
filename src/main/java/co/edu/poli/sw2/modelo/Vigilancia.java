package co.edu.poli.sw2.modelo;

public class Vigilancia extends Dron{

	private boolean deteccionTermica;
	
	public Vigilancia() {
		
	}
	
	public Vigilancia(int id, String serial, String modelo, double peso, boolean deteccionTermica) {
		super(id, serial, modelo, peso);
		this.deteccionTermica = deteccionTermica;
	}

	public boolean isDeteccionTermica() {
		return deteccionTermica;
	}

	public void setDeteccionTermica(boolean deteccionTermica) {
		this.deteccionTermica = deteccionTermica;
	}

	
}
