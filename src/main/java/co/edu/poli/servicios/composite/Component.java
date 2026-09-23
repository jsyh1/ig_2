package co.edu.poli.servicios.composite;

/**
 * Componente base del patrón Composite.
 *
 * <p>Define la operación común que deben implementar tanto
 * los componentes individuales como los componentes compuestos.</p>
 */
public interface Component {

    /**
     * Obtiene la representación del componente.
     *
     * @return representación textual del componente
     */
    String mostrar();
}