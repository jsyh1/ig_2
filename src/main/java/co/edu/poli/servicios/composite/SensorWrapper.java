package co.edu.poli.servicios.composite;

import co.edu.poli.sw2.modelo.Sensor;

public class SensorWrapper implements Component {

    private Sensor sensor;

    public SensorWrapper(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public String mostrar() {
        return sensor.getId()
                + " - "
                + sensor.getTipo()
                + " - "
                + sensor.getFabricante();
    }
}