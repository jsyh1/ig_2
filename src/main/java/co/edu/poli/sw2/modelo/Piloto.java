package co.edu.poli.sw2.modelo;

import java.util.List;

/**
 * Representa un piloto dentro del sistema de gestión de drones.
 *
 * <p>
 * Un piloto contiene información básica como su identificador,
 * nombre y número de teléfono. Además, mantiene una lista de las
 * misiones que tiene asociadas.
 * </p>
 *
 * @author Jsyh
 * @version 1.0
 */
public class Piloto {

    /**
     * Identificador único del piloto.
     */
    private int id;

    /**
     * Nombre del piloto.
     */
    private String nombre;

    /**
     * Número de teléfono del piloto.
     */
    private String telefono;

    /**
     * Lista de misiones asociadas al piloto.
     */
    private List<Mision> misiones;

    /**
     * Constructor que permite crear un piloto con sus datos principales.
     *
     * @param id identificador del piloto
     * @param nombre nombre del piloto
     * @param telefono número de teléfono del piloto
     */
    public Piloto(
            int id,
            String nombre,
            String telefono) {

        this.id = id;

        this.nombre = nombre;

        this.telefono = telefono;
    }

    /**
     * Obtiene el identificador del piloto.
     *
     * @return identificador del piloto
     */
    public int getId() {

        return id;
    }

    /**
     * Obtiene el nombre del piloto.
     *
     * @return nombre del piloto
     */
    public String getNombre() {

        return nombre;
    }

    /**
     * Obtiene el número de teléfono del piloto.
     *
     * @return número de teléfono del piloto
     */
    public String getTelefono() {

        return telefono;
    }

    /**
     * Obtiene la lista de misiones asociadas al piloto.
     *
     * @return lista de misiones del piloto
     */
    public List<Mision> getMisiones() {

        return misiones;
    }
}