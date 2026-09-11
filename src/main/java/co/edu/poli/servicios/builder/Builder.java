package co.edu.poli.servicios.builder;

import co.edu.poli.sw2.modelo.Vigilancia;

/**
 * Builder encargado de construir objetos de tipo {@link Vigilancia}.
 */
public class Builder {

    private int id;
    private String serial;
    private String modelo;
    private double peso;
    private boolean deteccionTermica;

    /**
     * Establece el identificador del dron.
     *
     * @param id identificador del dron
     * @return este Builder
     */
    public Builder id(int id) {
        this.id = id;
        return this;
    }

    /**
     * Establece el serial del dron.
     *
     * @param serial serial del dron
     * @return este Builder
     */
    public Builder serial(String serial) {
        this.serial = serial;
        return this;
    }

    /**
     * Establece el modelo del dron.
     *
     * @param modelo modelo del dron
     * @return este Builder
     */
    public Builder modelo(String modelo) {
        this.modelo = modelo;
        return this;
    }

    /**
     * Establece el peso del dron.
     *
     * @param peso peso del dron
     * @return este Builder
     */
    public Builder peso(double peso) {
        this.peso = peso;
        return this;
    }

    /**
     * Establece si el dron posee detección térmica.
     *
     * @param deteccionTermica indica si posee detección térmica
     * @return este Builder
     */
    public Builder deteccionTermica(boolean deteccionTermica) {
        this.deteccionTermica = deteccionTermica;
        return this;
    }

    /**
     * Obtiene el identificador configurado.
     *
     * @return identificador
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el serial configurado.
     *
     * @return serial
     */
    public String getSerial() {
        return serial;
    }

    /**
     * Obtiene el modelo configurado.
     *
     * @return modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Obtiene el peso configurado.
     *
     * @return peso
     */
    public double getPeso() {
        return peso;
    }

    /**
     * Obtiene el estado de detección térmica.
     *
     * @return true si tiene detección térmica
     */
    public boolean isDeteccionTermica() {
        return deteccionTermica;
    }

    /**
     * Construye el objeto Vigilancia con los valores configurados.
     *
     * @return objeto Vigilancia construido
     */
    public Vigilancia build() {
        return new Vigilancia(id, serial, modelo, peso, deteccionTermica);
    }
}