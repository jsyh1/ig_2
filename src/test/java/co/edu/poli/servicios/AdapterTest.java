package co.edu.poli.servicios;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import co.edu.poli.servicios.adapter.EscritorJson;
import co.edu.poli.servicios.adapter.ExportarMision;
import co.edu.poli.servicios.adapter.MisionJsonAdapter;
import co.edu.poli.sw2.modelo.Mision;

/**
 * Pruebas unitarias para el patrón Adapter (MisionJsonAdapter).
 */
class AdapterTest {

    private static final String RUTA_ESPERADA = "mision_1.json";

    /**
     * Limpia el archivo generado después de cada prueba,
     * para que no queden residuos entre ejecuciones.
     */
    @AfterEach
    void limpiarArchivoGenerado() {

        File archivo = new File(RUTA_ESPERADA);

        if (archivo.exists()) {
            archivo.delete();
        }
    }

    /**
     * Verifica que el Adapter exporte correctamente la misión
     * y devuelva true cuando la escritura fue exitosa.
     */
    @Test
    void exportarJSON_debeRetornarTrue() {

        Mision mision = new Mision(
                1,
                "Mision Inspección Cultivo Norte",
                "Recorrido de monitoreo agrícola con sensores térmicos",
                new Date(),
                "Finca La Esperanza, Cundinamarca"
        );

        ExportarMision exportador = new MisionJsonAdapter(new EscritorJson());

        boolean resultado = exportador.exportarJSON(mision);

        assertTrue(resultado, "El Adapter debería exportar la misión correctamente.");
    }

    /**
     * Verifica que el archivo .json realmente se haya creado en disco.
     */
    @Test
    void exportarJSON_debeCrearElArchivo() {

        Mision mision = new Mision(
                2,
                "Mision Patrullaje Perimetral",
                "Vigilancia nocturna del perímetro de la finca",
                new Date(),
                "Finca El Roble, Boyacá"
        );

        ExportarMision exportador = new MisionJsonAdapter(new EscritorJson());
        exportador.exportarJSON(mision);

        File archivo = new File("mision_2.json");

        assertTrue(archivo.exists(), "El archivo JSON de la misión debería haberse creado.");

        archivo.delete(); // limpieza específica de este test
    }

    /**
     * Verifica que el contenido del archivo generado tenga
     * los datos de la misión en formato JSON.
     */
    @Test
    void exportarJSON_debeContenerLosDatosCorrectos() throws IOException {

        Mision mision = new Mision(
                1,
                "Mision Inspección Cultivo Norte",
                "Recorrido de monitoreo agrícola con sensores térmicos",
                new Date(),
                "Finca La Esperanza, Cundinamarca"
        );

        ExportarMision exportador = new MisionJsonAdapter(new EscritorJson());
        exportador.exportarJSON(mision);

        String contenido = Files.readString(new File(RUTA_ESPERADA).toPath());

        assertTrue(contenido.contains("\"id\": 1"));
        assertTrue(contenido.contains("Mision Inspección Cultivo Norte"));
        assertTrue(contenido.contains("Finca La Esperanza, Cundinamarca"));
    }
}