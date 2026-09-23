package co.edu.poli.servicios.adapter;

import java.text.SimpleDateFormat;

import co.edu.poli.sw2.modelo.Mision;

/**
 * Adaptador encargado de convertir una misión al formato JSON
 * y almacenarla mediante {@link EscritorJson}.
 *
 * <p>Esta clase implementa la interfaz {@link ExportarMision},
 * permitiendo que el modelo {@link Mision} pueda ser exportado
 * utilizando el servicio de escritura de archivos.</p>
 */
public class MisionJsonAdapter implements ExportarMision {

    /**
     * Servicio utilizado para escribir el contenido JSON en un archivo.
     */
    private EscritorJson servicio;

    /**
     * Crea un adaptador para la exportación de misiones.
     *
     * @param servicio servicio encargado de escribir el archivo JSON
     */
    public MisionJsonAdapter(EscritorJson servicio) {

        this.servicio = servicio;
    }

    /**
     * Convierte una misión a JSON y la guarda en un archivo.
     *
     * <p>El archivo generado utiliza el identificador de la misión
     * para construir su nombre.</p>
     *
     * @param mision misión que se desea exportar
     * @return {@code true} si el archivo fue generado correctamente;
     *         {@code false} si ocurrió un error al escribirlo
     */
    @Override
    public boolean exportarJSON(Mision mision) {

        String json = convertirAJSON(mision);

        String rutaArchivo = "mision_" + mision.getId() + ".json";

        return servicio.escribirArchivo(json, rutaArchivo);
    }

    /**
     * Convierte una misión en una cadena con formato JSON.
     *
     * <p>La fecha se transforma al formato {@code yyyy-MM-dd}.
     * Si la fecha de la misión es {@code null}, se utiliza una cadena vacía.</p>
     *
     * @param mision misión que será convertida
     * @return representación JSON de la misión
     */
    private String convertirAJSON(Mision mision) {

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        String fechaTexto = (mision.getFecha() != null)
                ? formato.format(mision.getFecha())
                : "";

        return "{\n"
                + "  \"id\": " + mision.getId() + ",\n"
                + "  \"nombre\": \"" + mision.getNombre() + "\",\n"
                + "  \"descripcion\": \"" + mision.getDescripcion() + "\",\n"
                + "  \"fecha\": \"" + fechaTexto + "\",\n"
                + "  \"ubicacion\": \"" + mision.getUbicacion() + "\"\n"
                + "}";
    }
}