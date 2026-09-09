package co.edu.poli.servicios.drecorator;

import co.edu.poli.sw2.modelo.Dron;

public class Concrete implements Component {

    public Dron dron;

    public Concrete(Dron dron) {
        this.dron = dron;
    }

    @Override
    public String calcularConsumo(String n) {

        return "Componente: Dron\n"
                + "Serial: " + dron.getSerial() + "\n"
                + "Modelo: " + dron.getModelo() + "\n"
                + "Peso: " + dron.getPeso()
                + "\n"
                + "Batería: estándar";
    }
}