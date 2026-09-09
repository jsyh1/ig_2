package co.edu.poli.servicios.drecorator;

public class BateriaComponent implements Component {

    private Component component;

    public BateriaComponent(Component component) {
        this.component = component;
    }

    @Override
    public String calcularConsumo(String n) {

        return component.calcularConsumo(n)
                + "\n"
                + "Decorator: Batería ampliada";
    }
}