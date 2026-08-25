package co.edu.poli.sw2.modelo;

import java.util.List;

/**
 * Clase abstracta que representa un dron.
 *
 * <p>
 * Contiene los atributos y comportamientos generales que comparten
 * los diferentes tipos de drones del sistema.
 * </p>
 *
 * <p>
 * Esta clase puede ser extendida por clases especializadas como
 * {@link Agricultura} y {@link Vigilancia}.
 * </p>
 *
 * @author Jsyh
 * @version 1.0
 */
public abstract class Dron {

    /**
     * Identificador único del dron.
     */
    private int id;

    /**
     * Número serial del dron.
     */
    private String serial;

    /**
     * Modelo del dron.
     */
    private String modelo;

    /**
     * Peso del dron.
     */
    private double peso;

    /**
     * Misión asignada al dron.
     */
    private Mision mision;

    /**
     * Lista de sensores asociados al dron.
     */
    private List<Sensor> sensores;

    /**
     * Constructor vacío de la clase {@code Dron}.
     *
     * <p>
     * Se utiliza principalmente para permitir la creación de objetos
     * mediante constructores de las clases hijas.
     * </p>
     */
    public Dron() {

    }

    /**
     * Constructor que permite crear un dron con sus datos generales.
     *
     * @param id identificador del dron
     * @param serial número serial del dron
     * @param modelo modelo del dron
     * @param peso peso del dron
     */
    public Dron(
            int id,
            String serial,
            String modelo,
            double peso) {

        this.id = id;

        this.serial = serial;

        this.modelo = modelo;

        this.peso = peso;
    }

    /**
     * Obtiene el identificador del dron.
     *
     * @return identificador del dron
     */
    public int getId() {

        return id;
    }

    /**
     * Obtiene el número serial del dron.
     *
     * @return número serial del dron
     */
    public String getSerial() {

        return serial;
    }

    /**
     * Obtiene el modelo del dron.
     *
     * @return modelo del dron
     */
    public String getModelo() {

        return modelo;
    }

    /**
     * Obtiene el peso del dron.
     *
     * @return peso del dron
     */
    public double getPeso() {

        return peso;
    }

    /**
     * Obtiene la misión asignada al dron.
     *
     * @return misión asociada al dron
     */
    public Mision getMision() {

        return mision;
    }

    /**
     * Obtiene la lista de sensores asociados al dron.
     *
     * @return lista de sensores del dron
     */
    public List<Sensor> getSensores() {

        return sensores;
    }

    /**
     * Establece el identificador del dron.
     *
     * @param id nuevo identificador del dron
     */
    public void setId(int id) {

        this.id = id;
    }

    /**
     * Establece el número serial del dron.
     *
     * @param serial nuevo número serial del dron
     */
    public void setSerial(String serial) {

        this.serial = serial;
    }

    /**
     * Establece el modelo del dron.
     *
     * @param modelo nuevo modelo del dron
     */
    public void setModelo(String modelo) {

        this.modelo = modelo;
    }

    /**
     * Establece el peso del dron.
     *
     * @param peso nuevo peso del dron
     */
    public void setPeso(double peso) {

        this.peso = peso;
    }
}