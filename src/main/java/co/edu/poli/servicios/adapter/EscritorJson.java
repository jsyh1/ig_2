package co.edu.poli.servicios.adapter;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Servicio encargado de escribir contenido en archivos.
 *
 * <p>Esta clase proporciona una operación sencilla para almacenar
 * contenido, como una representación JSON, en un archivo.</p>
 */
public class EscritorJson {

    /**
     * Escribe contenido en un archivo especificado.
     *
     * @param contenido contenido que será escrito en el archivo
     * @param rutaArchivo ruta y nombre del archivo donde se almacenará
     *                    el contenido
     * @return {@code true} si el archivo fue escrito correctamente;
     *         {@code false} si ocurrió un error de entrada/salida
     */
    public boolean escribirArchivo(String contenido, String rutaArchivo) {

        try (FileWriter writer = new FileWriter(rutaArchivo)) {

            writer.write(contenido);

            return true;

        } catch (IOException e) {

            e.printStackTrace();

            return false;
        }
    }
}