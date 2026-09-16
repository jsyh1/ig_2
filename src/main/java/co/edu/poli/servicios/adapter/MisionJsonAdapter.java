package co.edu.poli.servicios.adapter;

import java.text.SimpleDateFormat;

import co.edu.poli.sw2.modelo.Mision;

public class MisionJsonAdapter implements ExportarMision {

    private EscritorJson servicio;

    public MisionJsonAdapter(EscritorJson servicio) {
        this.servicio = servicio;
    }

    @Override
    public boolean exportarJSON(Mision mision) {

        String json = convertirAJSON(mision);
        String rutaArchivo = "mision_" + mision.getId() + ".json";

        return servicio.escribirArchivo(json, rutaArchivo);
    }

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