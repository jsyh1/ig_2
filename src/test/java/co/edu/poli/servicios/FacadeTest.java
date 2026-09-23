package co.edu.poli.servicios;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import co.edu.poli.servicios.facade.DronFacade;
import co.edu.poli.sw2.modelo.Agricultura;
import co.edu.poli.sw2.modelo.Dron;
import co.edu.poli.sw2.modelo.Vigilancia;

/**
 * Pruebas unitarias para la clase {@link DronFacade}.
 *
 * <p>
 * Esta clase verifica el correcto funcionamiento del patrón de diseño
 * <b>Facade</b>, el cual proporciona una interfaz simplificada para acceder
 * a diferentes subsistemas de la aplicación.
 * </p>
 *
 * <p>
 * En este caso, {@link DronFacade} permite acceder mediante una única
 * interfaz a los patrones <b>Factory</b>, <b>Builder</b> y
 * <b>Prototype</b> utilizados para la creación y clonación de drones.
 * </p>
 *
 * <p>
 * Las pruebas comprueban tanto los casos exitosos como el comportamiento
 * esperado cuando se utilizan tipos de drones no soportados.
 * </p>
 *
 * @author Jsyh
 * @version 1.0
 * @see DronFacade
 * @see Dron
 * @see Agricultura
 * @see Vigilancia
 */
class FacadeTest {

    /**
     * Verifica que el Facade cree correctamente un dron de tipo
     * {@link Agricultura} mediante el patrón Factory.
     *
     * <p>
     * Se solicita al Facade la creación de un dron indicando el tipo
     * "Agricultura" y posteriormente se comprueba que el objeto creado
     * no sea {@code null} y que corresponda a una instancia de
     * {@link Agricultura}.
     * </p>
     */
    @Test
    void crearDronAgriculturaDesdeFactory() {

        DronFacade facade = new DronFacade();

        Dron dron = facade.crearDronDesdeFactory("Agricultura");

        assertNotNull(dron);
        assertInstanceOf(Agricultura.class, dron);
    }

    /**
     * Verifica que el Facade cree correctamente un dron de tipo
     * {@link Vigilancia} mediante el patrón Factory.
     *
     * <p>
     * Se solicita al Facade la creación de un dron indicando el tipo
     * "Vigilancia" y se comprueba que el resultado no sea {@code null}
     * y corresponda a una instancia de {@link Vigilancia}.
     * </p>
     */
    @Test
    void crearDronVigilanciaDesdeFactory() {

        DronFacade facade = new DronFacade();

        Dron dron = facade.crearDronDesdeFactory("Vigilancia");

        assertNotNull(dron);
        assertInstanceOf(Vigilancia.class, dron);
    }

    /**
     * Verifica que el Facade controle correctamente un tipo de dron
     * no soportado por el patrón Factory.
     *
     * <p>
     * Se utiliza el tipo "Militar", que no corresponde a los tipos
     * de drones disponibles en el sistema, y se comprueba que el
     * método lance una {@link IllegalArgumentException}.
     * </p>
     */
    @Test
    void crearDronFactoryTipoNoSoportado() {

        DronFacade facade = new DronFacade();

        assertThrows(
                IllegalArgumentException.class,
                () -> facade.crearDronDesdeFactory("Militar")
        );
    }

    /**
     * Verifica que el Facade construya correctamente un dron de tipo
     * {@link Vigilancia} mediante el patrón Builder.
     *
     * <p>
     * Se proporcionan los datos necesarios para construir el dron y
     * posteriormente se comprueba que sus atributos correspondan a
     * los valores enviados al Facade.
     * </p>
     */
    @Test
    void crearDronConBuilder() {

        DronFacade facade = new DronFacade();

        Vigilancia dron = facade.crearDronConBuilder(
                1,
                "DR-001",
                "DJI Mavic",
                2.5,
                true
        );

        assertNotNull(dron);
        assertEquals(1, dron.getId());
        assertEquals("DR-001", dron.getSerial());
        assertEquals("DJI Mavic", dron.getModelo());
        assertEquals(2.5, dron.getPeso());
        assertTrue(dron.isDeteccionTermica());
    }

    /**
     * Verifica que el Facade permita clonar correctamente un dron
     * utilizando el patrón Prototype.
     *
     * <p>
     * Se crea un dron original de tipo {@link Vigilancia} y se utiliza
     * el método del Facade para generar un clon. Posteriormente se
     * comprueba que el clon conserve los mismos valores de los atributos
     * del objeto original.
     * </p>
     *
     * <p>
     * También se verifica mediante {@link #assertNotSame(Object, Object)}
     * que el clon sea una instancia diferente al objeto original,
     * demostrando el comportamiento esperado del patrón Prototype.
     * </p>
     */
    @Test
    void clonarDron() {

        DronFacade facade = new DronFacade();

        Vigilancia original = new Vigilancia(
                1,
                "DR-001",
                "DJI Mavic",
                2.5,
                true
        );

        Dron clon = facade.clonarDron(original);

        assertNotNull(clon);
        assertInstanceOf(Vigilancia.class, clon);

        assertEquals(original.getId(), clon.getId());
        assertEquals(original.getSerial(), clon.getSerial());
        assertEquals(original.getModelo(), clon.getModelo());
        assertEquals(original.getPeso(), clon.getPeso());

        Vigilancia clonVigilancia = (Vigilancia) clon;

        assertEquals(
                original.isDeteccionTermica(),
                clonVigilancia.isDeteccionTermica()
        );

        // El clon debe ser un objeto diferente al original.
        assertNotSame(original, clon);
    }

    /**
     * Verifica que el Facade controle correctamente el intento de
     * clonar un tipo de dron no soportado por el patrón Prototype.
     *
     * <p>
     * Se crea una instancia anónima de {@link Dron} que no corresponde
     * a ninguno de los tipos de drones soportados por el sistema.
     * Se comprueba que el Facade lance una
     * {@link IllegalArgumentException} al intentar clonarla.
     * </p>
     */
    @Test
    void clonarDronTipoNoSoportado() {

        DronFacade facade = new DronFacade();

        Dron dron = new Dron() {
        };

        assertThrows(
                IllegalArgumentException.class,
                () -> facade.clonarDron(dron)
        );
    }
}

