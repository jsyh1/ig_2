package co.edu.poli.servicios;
import co.edu.poli.sw2.modelo.Agricultura;
import co.edu.poli.sw2.modelo.Dron;
import co.edu.poli.sw2.modelo.Vigilancia;

public class DronPrototype implements Prototype<Dron> {
    private Dron dron;

    public DronPrototype(Dron dron) {
        this.dron = dron;
    }

    @Override
    public Dron clonar() {
        if(dron instanceof Agricultura) {
           Agricultura original = (Agricultura) dron;

            Agricultura clon = new Agricultura();

            clon.setId(original.getId());
            clon.setSerial(original.getSerial());
            clon.setModelo(original.getModelo());
            clon.setPeso(original.getPeso());

            clon.setCapacidadTanque(original.getCapacidadTanque());
        return clon;

    } if(dron instanceof Vigilancia) {
            Vigilancia original = (Vigilancia) dron;

            Vigilancia clon = new Vigilancia();

            clon.setId(original.getId());
            clon.setSerial(original.getSerial());
            clon.setModelo(original.getModelo());
            clon.setPeso(original.getPeso());

            clon.setDeteccionTermica(original.isDeteccionTermica());
        return clon;
        }
    throw new IllegalArgumentException("Tipo de dron no soportado para clonación");
    }
}
