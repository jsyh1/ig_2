package co.edu.poli.servicios.proxy;

import co.edu.poli.sw2.dao.DronDAO;
import co.edu.poli.sw2.dao.DronDAOImplementado;

/**
 * Implementación real del servicio de eliminación de drones.
 *
 * <p>Esta clase representa el objeto real dentro del patrón Proxy.
 * Su responsabilidad es ejecutar directamente la operación de eliminación
 * utilizando el DAO correspondiente.</p>
 */
public class ServicioEliminarReal implements ServicioEliminar {

    /**
     * DAO utilizado para acceder a los datos de los drones.
     */
    private final DronDAO dronDAO;

    /**
     * Crea una instancia del servicio real e inicializa el DAO
     * encargado de las operaciones sobre drones.
     */
    public ServicioEliminarReal() {

        this.dronDAO = new DronDAOImplementado();
    }

    /**
     * Elimina un dron utilizando su identificador.
     *
     * @param id identificador del dron que se desea eliminar
     * @return {@code true} si el dron fue eliminado correctamente;
     *         {@code false} si la operación no pudo realizarse
     */
    @Override
    public boolean eliminar(int id) {

        return dronDAO.eliminar(id);
    }
}