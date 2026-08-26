package co.edu.poli.servicios;

import co.edu.poli.sw2.modelo.Dron;

/**
 * Interfaz que define el contrato para las fábricas encargadas
 * de crear diferentes tipos de drones.
 *
 * <p>
 * Cada clase que implemente esta interfaz debe proporcionar
 * su propia implementación del método {@link #crearDrone()}.
 * </p>
 */
public interface factoriaDrones {

	/**
	 * Crea una instancia de un tipo específico de dron.
	 *
	 * @return un objeto {@link Dron} correspondiente al tipo de dron
	 *         que implemente la fábrica
	 */
	Dron crearDrone();

}