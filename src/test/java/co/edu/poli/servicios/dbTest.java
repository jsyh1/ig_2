package co.edu.poli.servicios;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class dbTest  {

    @Test
    void debeTenerUnaUnicaInstancia() {

    	db  instancia1 = db.getInstancia();
    	db instancia2 = db.getInstancia();

        assertSame(instancia1, instancia2);
    }
}