package co.edu.poli.sw2.modelo;

import java.util.Date;

/**
 * Representa una misión asignada a un dron dentro del sistema.
 *
 * <p>
 * Una misión contiene información como su identificador, nombre,
 * descripción y fecha. También mantiene las relaciones con el
 * {@link Piloto} y el {@link Dron} asociados.
 * </p>
 *
 * @author Jsyh
 * @version 1.0
 */
public class Mision {

    /**
     * Identificador único de la misión.
     */
    private int id;

    /**
     * Nombre de la misión.
     */
    private String nombre;

    /**
     * Descripción de las actividades que se realizarán durante la misión.
     */
    private String descripcion;

    /**
     * Fecha programada para la misión.
     */
    private Date fecha;

    /**
     * Piloto encargado de la misión.
     */
    private Piloto piloto;

    /**
     * Dron asignado para realizar la misión.
     */
    private Dron dron;

    /**
     * Constructor que permite crear una misión con sus datos principales.
     *
     * @param id identificador de la misión
     * @param nombre nombre de la misión
     * @param descripcion descripción de la misión
     * @param fecha fecha programada para la misión
     */
    public Mision(
            int id,
            String nombre,
            String descripcion,
            Date fecha) {

        this.id = id;

        this.nombre = nombre;

        this.descripcion = descripcion;

        this.fecha = fecha;
    }

    /**
     * Obtiene el identificador de la misión.
     *
     * @return identificador de la misión
     */
    public int getId() {

        return id;
    }

    /**
     * Obtiene el nombre de la misión.
     *
     * @return nombre de la misión
     */
    public String getNombre() {

        return nombre;
    }

    /**
     * Obtiene la descripción de la misión.
     *
     * @return descripción de la misión
     */
    public String getDescripcion() {

        return descripcion;
    }

    /**
     * Obtiene la fecha programada para la misión.
     *
     * @return fecha de la misión
     */
    public Date getFecha() {

        return fecha;
    }

    /**
     * Obtiene el piloto encargado de la misión.
     *
     * @return piloto asociado a la misión
     */
    public Piloto getPiloto() {

        return piloto;
    }

    /**
     * Obtiene el dron asignado a la misión.
     *
     * @return dron asociado a la misión
     */
    public Dron getDrone() {

        return dron;
    }
}