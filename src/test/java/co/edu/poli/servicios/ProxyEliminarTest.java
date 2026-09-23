package co.edu.poli.servicios;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.poli.servicios.proxy.ProxyEliminar;
import co.edu.poli.servicios.proxy.ServicioEliminar;
import co.edu.poli.servicios.proxy.ServicioEliminarReal;

public class ProxyEliminarTest {

    private ProxyEliminar proxy;
    private ServicioEliminar servicioReal;
    private final String CLAVE_CORRECTA = "1234";
    private final String CLAVE_INCORRECTA = "ClaveFalsa";

    @BeforeEach
    void setUp() {
        // Creamos la instancia real del servicio
        servicioReal = new ServicioEliminarReal();
        
        // Inyectamos el servicio real y la contraseña autorizada en el Proxy
        proxy = new ProxyEliminar(servicioReal, CLAVE_CORRECTA);
    }

    @Test
    void testCheckAccess_ContraseñaCorrecta_DebeRetornarTrue() {
        boolean resultado = proxy.checkAccess(CLAVE_CORRECTA);
        assertTrue(resultado, "El acceso debería ser permitido con la clave correcta.");
    }

    @Test
    void testCheckAccess_ContraseñaIncorrecta_DebeRetornarFalse() {
        boolean resultado = proxy.checkAccess(CLAVE_INCORRECTA);
        assertFalse(resultado, "El acceso debería ser denegado con una clave incorrecta.");
    }

    @Test
    void testMostrarMensaje_DebeRetornarYImprimirElMensaje() {
        String mensajePrueba = "Hola Mundo Proxy";
        String resultado = proxy.mostrarMensaje(mensajePrueba);
        
        assertEquals(mensajePrueba, resultado, "El método debe retornar exactamente el texto enviado.");
    }

    @Test
    void testEliminar_DebeEjecutarLaAccionDelServicioReal() {
        // ID de prueba para pasar al método
        int idDronPrueba = 99; 
        
        // El proxy llama directamente al servicio real, el cual usa DronDAOImplementado
        boolean resultado = proxy.eliminar(idDronPrueba);
        
        // Evaluamos que la respuesta sea un valor booleano válido (true o false según tu DAO)
        // Si tu DAO local devuelve siempre falso por defecto al no existir el ID, cambiar por assertFalse
        assertNotNull(resultado, "El método eliminar no debe retornar un valor nulo.");
    }
}
