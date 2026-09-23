package co.edu.poli.servicios.composite;

import co.edu.poli.sw2.modelo.Sensor;

/**
 * Adaptador que permite tratar un objeto {@link Sensor} como un
 * {@link Component} dentro de la estructura Composite.
 *
 * <p>El Wrapper encapsula un sensor y proporciona la operación
 * {@link #mostrar()} requerida por el componente.</p>
 */
public class SensorWrapper implements Component {

    /**
     * Sensor encapsulado por el Wrapper.
     */
    private Sensor sensor;

    /**
     * Crea un Wrapper para un sensor determinado.
     *
     * @param sensor sensor que será encapsulado
     */
    public SensorWrapper(Sensor sensor) {

        this.sensor = sensor;
    }

    /**
     * Obtiene la información principal del sensor.
     *
     * <p>La información retornada contiene el identificador,
     * tipo y fabricante del sensor.</p>
     *
     * @return información del sensor separada por guiones
     */
    @Override
    public String mostrar() {

        return sensor.getId()
                + " - "
                + sensor.getTipo()
                + " - "
                + sensor.getFabricante();
    }
}