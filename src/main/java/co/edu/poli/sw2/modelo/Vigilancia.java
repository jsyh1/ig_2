package co.edu.poli.sw2.modelo;

/**
 * Representa un dron especializado en actividades de vigilancia.
 *
 * <p>
 * Esta clase hereda los atributos generales de {@link Dron} y agrega
 * la característica de detección térmica propia de los drones de vigilancia.
 * </p>
 *
 * @author Jsyh
 * @version 1.0
 */
public class Vigilancia extends Dron {

	/**
	 * Indica si el dron cuenta con capacidad de detección térmica.
	 */
	private boolean deteccionTermica;

	/**
	 * Constructor vacío de la clase {@code Vigilancia}.
	 */
	public Vigilancia() {

	}

	/**
	 * Constructor que permite crear un dron de vigilancia con todos
	 * sus atributos.
	 *
	 * @param id identificador del dron
	 * @param serial número serial del dron
	 * @param modelo modelo del dron
	 * @param peso peso del dron
	 * @param deteccionTermica indica si el dron cuenta con detección térmica
	 */
	public Vigilancia(
			int id,
			String serial,
			String modelo,
			double peso,
			boolean deteccionTermica) {

		super(id, serial, modelo, peso);

		this.deteccionTermica = deteccionTermica;
	}

	/**
	 * Comprueba si el dron cuenta con detección térmica.
	 *
	 * @return {@code true} si cuenta con detección térmica;
	 *         {@code false} en caso contrario
	 */
	public boolean isDeteccionTermica() {

		return deteccionTermica;
	}

	/**
	 * Establece si el dron cuenta con capacidad de detección térmica.
	 *
	 * @param deteccionTermica nuevo estado de la detección térmica
	 */
	public void setDeteccionTermica(boolean deteccionTermica) {

		this.deteccionTermica = deteccionTermica;
	}
}