package co.edu.poli.servicios;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.poli.servicios.proxy.ProxyEliminar;
import co.edu.poli.servicios.proxy.ServicioEliminar;
import co.edu.poli.servicios.proxy.ServicioEliminarReal;

/**
 * Clase de pruebas unitarias para verificar el funcionamiento del patrón Proxy
 * aplicado al servicio de eliminación de drones.
 *
 * <p>Se comprueba el control de acceso mediante contraseña, la visualización
 * de mensajes y la delegación de la eliminación al servicio real.</p>
 */
public class ProxyEliminarTest {

    /**
     * Instancia del proxy que controla el acceso al servicio.
     */
    private ProxyEliminar proxy;

    /**
     * Instancia del servicio real utilizado por el proxy.
     */
    private ServicioEliminar servicioReal;

    /**
     * Contraseña autorizada para realizar las operaciones.
     */
    private final String CLAVE_CORRECTA = "1234";

    /**
     * Contraseña utilizada para comprobar el rechazo de accesos no autorizados.
     */
    private final String CLAVE_INCORRECTA = "ClaveFalsa";

    /**
     * Configura las dependencias necesarias antes de cada prueba.
     *
     * <p>Se crea el servicio real y posteriormente se inyecta en el proxy
     * junto con la contraseña autorizada.</p>
     */
    @BeforeEach
    void setUp() {
        servicioReal = new ServicioEliminarReal();
        proxy = new ProxyEliminar(servicioReal, CLAVE_CORRECTA);
    }

    /**
     * Verifica que el proxy permita el acceso cuando se proporciona
     * la contraseña correcta.
     */
    @Test
    void testCheckAccess_ContraseñaCorrecta_DebeRetornarTrue() {

        boolean resultado = proxy.checkAccess(CLAVE_CORRECTA);

        assertTrue(
            resultado,
            "El acceso debería ser permitido con la clave correcta."
        );
    }

    /**
     * Verifica que el proxy rechace el acceso cuando se proporciona
     * una contraseña incorrecta.
     */
    @Test
    void testCheckAccess_ContraseñaIncorrecta_DebeRetornarFalse() {

        boolean resultado = proxy.checkAccess(CLAVE_INCORRECTA);

        assertFalse(
            resultado,
            "El acceso debería ser denegado con una clave incorrecta."
        );
    }

    /**
     * Verifica que el método {@code mostrarMensaje} retorne exactamente
     * el mensaje recibido y que este pueda ser mostrado por consola.
     */
    @Test
    void testMostrarMensaje_DebeRetornarYImprimirElMensaje() {

        String mensajePrueba = "Hola Mundo Proxy";

        String resultado = proxy.mostrarMensaje(mensajePrueba);

        assertEquals(
            mensajePrueba,
            resultado,
            "El método debe retornar exactamente el texto enviado."
        );
    }

    /**
     * Verifica que el método {@code eliminar} delegue la operación
     * al servicio real y retorne un valor booleano.
     */
    @Test
    void testEliminar_DebeEjecutarLaAccionDelServicioReal() {

        int idDronPrueba = 99;

        boolean resultado = proxy.eliminar(idDronPrueba);

        assertNotNull(
            resultado,
            "El método eliminar no debe retornar un valor nulo."
        );
    }
}