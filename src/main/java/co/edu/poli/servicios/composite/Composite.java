package co.edu.poli.servicios.composite;

import java.util.ArrayList;
import java.util.List;

public class Composite implements Component {

    private List<Component> hijos;

    public Composite() {
        hijos = new ArrayList<>();
    }

    public String agregar(String componente) {
        // Implementación
        return "Componente agregado: " + componente;
    }

    public String eliminar(String componente) {
        // Implementación
        return "Componente eliminado: " + componente;
    }

    @Override
    public String mostrar() {
        String resultado = "";

        for (Component hijo : hijos) {
            resultado += hijo.mostrar() + "\n";
        }

        return resultado;
    }
}