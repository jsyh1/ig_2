package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

import co.edu.poli.sw2.servicios.db;

class dbtest {

    @Test
    void testConexion() {

        db baseDatos = db.getInstancia();

        assertNotNull(baseDatos);

        Connection conexion = baseDatos.getConexion();

        assertNotNull(conexion);

        try {
            assertFalse(conexion.isClosed());
        } catch (Exception e) {
            fail("No se pudo comprobar la conexion: " + e.getMessage());
        }
    }
}