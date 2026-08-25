package co.edu.poli.sw2.modelo;

/**
 * Representa un sensor asociado a un dron dentro del sistema.
 *
 * <p>
 * Un sensor contiene información sobre su identificador, tipo y fabricante.
 * Además, mantiene una referencia al {@link Dron} al que se encuentra
 * asociado.
 * </p>
 *
 * @author Jsyh
 * @version 1.0
 */
public class Sensor {

    /**
     * Identificador único del sensor.
     */
    private int id;

    /**
     * Tipo de sensor.
     */
    private String tipo;

    /**
     * Fabricante del sensor.
     */
    private String fabricante;

    /**
     * Dron al que se encuentra asociado el sensor.
     */
    private Dron dron;

    /**
     * Constructor que permite crear un sensor con sus datos principales.
     *
     * @param id identificador del sensor
     * @param tipo tipo de sensor
     * @param fabricante fabricante del sensor
     */
    public Sensor(
            int id,
            String tipo,
            String fabricante) {

        this.id = id;

        this.tipo = tipo;

        this.fabricante = fabricante;
    }

    /**
     * Obtiene el identificador del sensor.
     *
     * @return identificador del sensor
     */
    public int getId() {

        return id;
    }

    /**
     * Obtiene el tipo de sensor.
     *
     * @return tipo de sensor
     */
    public String getTipo() {

        return tipo;
    }

    /**
     * Obtiene el fabricante del sensor.
     *
     * @return fabricante del sensor
     */
    public String getFabricante() {

        return fabricante;
    }

    /**
     * Obtiene el dron asociado al sensor.
     *
     * @return dron al que pertenece el sensor
     */
    public Dron getDrone() {

        return dron;
    }
}