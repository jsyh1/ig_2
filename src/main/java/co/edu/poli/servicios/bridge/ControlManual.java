package co.edu.poli.servicios.bridge;

public class ControlManual implements ControlDron {

    @Override
    public String TipoControl() {
        return "El dron será operado mediante Control Manual (por un piloto).";
    }
}