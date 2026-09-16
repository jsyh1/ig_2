package co.edu.poli.servicios.adapter;

import java.io.FileWriter;
import java.io.IOException;

public class EscritorJson {
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
