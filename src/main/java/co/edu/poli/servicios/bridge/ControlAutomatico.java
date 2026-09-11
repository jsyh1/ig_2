package co.edu.poli.servicios.bridge;

public class ControlAutomatico implements ControlDron {

    @Override
    public String TipoControl() {
        return "El dron será operado mediante Control Automático (piloto automático / IA).";
    }
}