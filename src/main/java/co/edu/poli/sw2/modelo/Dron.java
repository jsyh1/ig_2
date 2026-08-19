package co.edu.poli.sw2.modelo;

import java.util.List;

public abstract class Dron {

    private int id;
    private String serial;
    private String modelo;
    private double peso;

    private Mision mision;
    private List<Sensor> sensores;
    
    public Dron() {
    	
    }
    
    public Dron(int id, String serial, String modelo, double peso) {
        this.id = id;
        this.serial = serial;
        this.modelo = modelo;
        this.peso = peso;
    }

    public int getId() {
        return id;
    }

    public String getSerial() {
        return serial;
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
    
    public void setId(int id) {
        this.id = id;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
}
