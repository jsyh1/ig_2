package co.edu.poli.sw2.modelo;

/**
 * Representa un dron especializado en actividades de agricultura.
 *
 * <p>
 * Esta clase hereda los atributos generales de {@link Dron} y agrega
 * la capacidad del tanque como característica específica de los drones
 * de agricultura.
 * </p>
 *
 * @author Jsyh
 * @version 1.0
 */
public class Agricultura extends Dron {

	/**
	 * Capacidad del tanque del dron de agricultura.
	 */
	private double capacidadTanque;

	/**
	 * Constructor vacío de la clase {@code Agricultura}.
	 */
	public Agricultura() {

	}

	/**
	 * Constructor que permite crear un dron de agricultura con todos
	 * sus atributos.
	 *
	 * @param id identificador del dron
	 * @param serial número serial del dron
	 * @param modelo modelo del dron
	 * @param peso peso del dron
	 * @param capacidadTanque capacidad del tanque del dron
	 */
	public Agricultura(
			int id,
			String serial,
			String modelo,
			double peso,
			double capacidadTanque) {

		super(id, serial, modelo, peso);

		this.capacidadTanque = capacidadTanque;
	}

	/**
	 * Obtiene la capacidad del tanque del dron.
	 *
	 * @return capacidad del tanque
	 */
	public double getCapacidadTanque() {

		return capacidadTanque;
	}

	/**
	 * Establece la capacidad del tanque del dron.
	 *
	 * @param capacidadTanque nueva capacidad del tanque
	 */
	public void setCapacidadTanque(double capacidadTanque) {

		this.capacidadTanque = capacidadTanque;
	}
}