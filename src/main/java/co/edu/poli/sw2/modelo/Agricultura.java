package co.edu.poli.sw2.modelo;

public class Agricultura extends Dron{
	
	private double capacidadTanque;

	public Agricultura(int id, String serial, String modelo, double peso, double capacidadTanque) {
		super(id, serial, modelo, peso);
		this.capacidadTanque = capacidadTanque;
	}

	public double getCapacidadTanque() {
		return capacidadTanque;
	}

	public void setCapacidadTanque(double capacidadTanque) {
		this.capacidadTanque = capacidadTanque;
	}
	
	
}
