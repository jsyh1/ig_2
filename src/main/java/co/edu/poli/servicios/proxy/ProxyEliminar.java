package co.edu.poli.servicios.proxy;

/**
 * Proxy que controla el acceso al servicio de eliminación de drones.
 *
 * <p>Implementa la interfaz {@link ServicioEliminar} y actúa como intermediario
 * entre el cliente y el servicio real. Además, permite validar una contraseña
 * antes de utilizar las operaciones protegidas.</p>
 */
public class ProxyEliminar implements ServicioEliminar {

    /**
     * Referencia al servicio real que ejecutará la operación.
     */
    private final ServicioEliminar realService;

    /**
     * Contraseña requerida para autorizar el acceso.
     */
    private final String contraseñaAutorizada;

    /**
     * Crea una instancia del proxy.
     *
     * @param servicio servicio real que será utilizado por el proxy
     * @param contraseña contraseña autorizada para acceder al servicio
     */
    public ProxyEliminar(ServicioEliminar servicio, String contraseña) {

        this.realService = servicio;
        this.contraseñaAutorizada = contraseña;
    }

    /**
     * Comprueba si una contraseña coincide con la contraseña autorizada.
     *
     * @param contraseña contraseña proporcionada por el usuario
     * @return {@code true} si la contraseña es correcta;
     *         {@code false} en caso contrario
     */
    public boolean checkAccess(String contraseña) {

        return contraseñaAutorizada.equals(contraseña);
    }

    /**
     * Solicita al servicio real la eliminación de un dron.
     *
     * @param id identificador del dron que se desea eliminar
     * @return {@code true} si la eliminación fue realizada correctamente;
     *         {@code false} en caso contrario
     */
    @Override
    public boolean eliminar(int id) {

        return realService.eliminar(id);
    }

    /**
     * Muestra un mensaje en la consola.
     *
     * @param mensaje texto que se desea mostrar
     * @return el mismo mensaje recibido
     */
    public String mostrarMensaje(String mensaje) {

        System.out.println(mensaje);

        return mensaje;
    }
}