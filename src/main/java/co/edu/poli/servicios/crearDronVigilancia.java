package co.edu.poli.servicios;

import co.edu.poli.sw2.modelo.Dron;
import co.edu.poli.sw2.modelo.Vigilancia;

/**
 * Fábrica encargada de crear drones especializados en vigilancia.
 * Implementa la interfaz {@link factoriaDrones}.
 */
public class crearDronVigilancia implements factoriaDrones {

	/**
	 * Crea una instancia de un dron de tipo vigilancia.
	 *
	 * @return un nuevo objeto {@link Vigilancia} como {@link Dron}
	 */
	@Override
	public Dron crearDrone() {

		return new Vigilancia();
	}
}