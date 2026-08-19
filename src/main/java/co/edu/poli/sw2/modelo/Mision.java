package co.edu.poli.sw2.modelo;


import java.util.Date;

public class Mision {

    private int id;
    private String nombre;
    private String descripcion;
    private Date fecha;

    private Piloto piloto;
    private Dron dron;

    public Mision(int id, String nombre, String descripcion, Date fecha) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public Piloto getPiloto() {
        return piloto;
    }

    public Dron getDrone() {
        return dron;
    }
}