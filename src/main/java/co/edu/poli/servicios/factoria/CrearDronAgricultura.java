package co.edu.poli.servicios.factoria;

import co.edu.poli.sw2.modelo.Agricultura;
import co.edu.poli.sw2.modelo.Dron;

/**
 * Fábrica encargada de crear drones especializados en agricultura.
 * Implementa la interfaz {@link FactoriaDrones}.
 */
public class CrearDronAgricultura implements FactoriaDrones {

	/**
	 * Crea una instancia de un dron de tipo agricultura.
	 *
	 * @return un nuevo objeto {@link Agricultura} como {@link Dron}
	 */
	@Override
	public Dron crearDrone() {

		return new Agricultura();
	}
}