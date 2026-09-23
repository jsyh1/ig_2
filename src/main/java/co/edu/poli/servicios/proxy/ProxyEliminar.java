package co.edu.poli.servicios.proxy;

public class ProxyEliminar implements ServicioEliminar {

    private final ServicioEliminar realService;
    private final String contraseñaAutorizada;

    public ProxyEliminar(ServicioEliminar servicio, String contraseña) {
        this.realService = servicio;
        this.contraseñaAutorizada = contraseña;
    }

    public boolean checkAccess(String contraseña) {
        return contraseñaAutorizada.equals(contraseña);
    }

    @Override
    public boolean eliminar(int id) {
        return realService.eliminar(id);
    }

    public String mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
        return mensaje;
    }
}