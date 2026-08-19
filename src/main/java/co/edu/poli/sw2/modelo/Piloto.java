package co.edu.poli.sw2.modelo;

import java.util.List;

public class Piloto {

    private int id;
    private String nombre;
    private String telefono;

    private List<Mision> misiones;

    public Piloto(int id, String nombre, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public List<Mision> getMisiones() {
        return misiones;
    }
}