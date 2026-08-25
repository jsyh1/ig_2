package co.edu.poli.servicios;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Clase de pruebas unitarias para verificar el funcionamiento
 * del patrón Singleton implementado en {@link db}.
 *
 * <p>
 * Esta clase comprueba que la clase {@code db} mantenga una única
 * instancia durante la ejecución del programa.
 * </p>
 *
 * @author Jsyh
 * @version 1.0
 */
public class dbTest {

    /**
     * Verifica que dos llamadas consecutivas al método
     * {@link db#getInstancia()} devuelvan exactamente la misma instancia.
     *
     * <p>
     * Utiliza {@code assertSame} para comprobar que ambas referencias
     * apuntan al mismo objeto en memoria.
     * </p>
     */
    @Test
    void debeTenerUnaUnicaInstancia() {

        db instancia1 = db.getInstancia();
        db instancia2 = db.getInstancia();

        assertSame(instancia1, instancia2);
    }
}