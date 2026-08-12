package co.edu.poli.sw2.modelo;

import java.util.List;

public class Dron {

    private int id;
    private double senal;
    private String modelo;
    private double peso;

    private Mision mision;
    private List<Sensor> sensores;

    public Dron(int id, double senal, String modelo, double peso) {
        this.id = id;
        this.senal = senal;
        this.modelo = modelo;
        this.peso = peso;
    }

    public int getId() {
        return id;
    }

    public double getSenal() {
        return senal;
    }

    public String getModelo() {
        return modelo;
    }

    public double getPeso() {
        return peso;
    }

    public Mision getMision() {
        return mision;
    }

    public List<Sensor> getSensores() {
        return sensores;
    }	
}
