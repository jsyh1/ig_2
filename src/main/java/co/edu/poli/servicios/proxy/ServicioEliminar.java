package co.edu.poli.servicios.proxy;

/**
 * Define las operaciones disponibles para el servicio de eliminación
 * de drones.
 *
 * <p>Esta interfaz permite desacoplar el proxy del servicio real,
 * facilitando la aplicación del patrón de diseño Proxy.</p>
 */
public interface ServicioEliminar {

    /**
     * Elimina un dron a partir de su identificador.
     *
     * @param id identificador del dron que se desea eliminar
     * @return {@code true} si la eliminación fue exitosa;
     *         {@code false} si no fue posible realizarla
     */
    boolean eliminar(int id);
}