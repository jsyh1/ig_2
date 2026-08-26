package co.edu.poli.servicios;

import co.edu.poli.sw2.modelo.Vigilancia;

public class builder {
	private int id;
	private String serial;
	private String modelo;
	private double peso;
	private boolean deteccionTermica;

	public builder id(int id) {
		this.id = id;
		return this;
	}

	public builder serial(String serial) {
		this.serial = serial;
		return this;
	}

	public builder modelo(String modelo) {
		this.modelo = modelo;
		return this;
	}

	public builder peso(double peso) {
		this.peso = peso;
		return this;
	}

	public builder deteccionTermica(boolean deteccionTermica) {
		this.deteccionTermica = deteccionTermica;
		return this;
	}

	public Vigilancia build() {
		return new Vigilancia(id, serial, modelo, peso, deteccionTermica);
	}
}
