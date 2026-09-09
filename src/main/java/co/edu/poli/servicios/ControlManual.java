package co.edu.poli.servicios;

public class ControlManual implements ControlDron {

    @Override
    public String TipoControl() {
        return "El dron será operado mediante Control Manual (por un piloto).";
    }
}