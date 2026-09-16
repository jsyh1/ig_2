package co.edu.poli.servicios;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import co.edu.poli.servicios.composite.Composite;
/**
 * Pruebas unitarias para la clase Composite.
 */
class CompositeTest {

    /**
     * Prueba que un Composite pueda crearse correctamente.
     */
    @Test
    void crearComposite() {

        Composite composite = new Composite();

        assertEquals("", composite.mostrar());
    }

    /**
     * Prueba el método agregar.
     */
    @Test
    void agregarComponente() {

        Composite composite = new Composite();

        String resultado = composite.agregar("Sensor Temperatura");

        assertEquals(
                "Componente agregado: Sensor Temperatura",
                resultado
        );
    }

    /**
     * Prueba el método eliminar.
     */
    @Test
    void eliminarComponente() {

        Composite composite = new Composite();

        String resultado = composite.eliminar("Sensor Temperatura");

        assertEquals(
                "Componente eliminado: Sensor Temperatura",
                resultado
        );
    }

    /**
     * Prueba el método mostrar cuando el Composite está vacío.
     */
    @Test
    void mostrarCompositeVacio() {

        Composite composite = new Composite();

        assertEquals("", composite.mostrar());
    }
}
