package co.edu.poli.servicios.adapter;

import co.edu.poli.sw2.modelo.Mision;

/**
 * Interfaz que define la operación de exportación de una misión.
 *
 * <p>Forma parte de la estructura utilizada para implementar
 * el patrón Adapter.</p>
 */
public interface ExportarMision {

    /**
     * Exporta una misión en formato JSON.
     *
     * @param mision misión que será convertida y exportada
     * @return {@code true} si la exportación fue exitosa;
     *         {@code false} si ocurrió un error durante la exportación
     */
    boolean exportarJSON(Mision mision);
}