package co.edu.poli.sw2.modelo;

public class Sensor {

    private int id;
    private String tipo;
    private String fabricante;

    private Dron dron;

    public Sensor(int id, String tipo, String fabricante) {
        this.id = id;
        this.tipo = tipo;
        this.fabricante = fabricante;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public Dron getDrone() {
        return dron;
    }
}