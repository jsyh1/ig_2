package co.edu.poli.servicios.facade;

import co.edu.poli.servicios.builder.Builder;
import co.edu.poli.servicios.factoria.CrearDronAgricultura;
import co.edu.poli.servicios.factoria.CrearDronVigilancia;
import co.edu.poli.servicios.factoria.FactoriaDrones;
import co.edu.poli.servicios.prototype.Prototype;
import co.edu.poli.servicios.prototype.DronPrototype;
import co.edu.poli.sw2.modelo.Dron;
import co.edu.poli.sw2.modelo.Vigilancia;

/* El cliente (DronController) solo interactúa con esta clase,
 * sin necesitar conocer los 3 mecanismos de creación por separado.
 */
public class DronFacade {

    private FactoriaDrones factoria;
    private Builder builder;
    private Prototype<Dron> prototype;

    /**
     * Crea un dron a partir del Factory Method, según el tipo indicado.
     *
     * @param tipo "Agricultura" o "Vigilancia"
     * @return el dron creado
     */
    public Dron crearDronDesdeFactory(String tipo) {

        if (tipo.equalsIgnoreCase("Agricultura")) {
            factoria = new CrearDronAgricultura();
        } else if (tipo.equalsIgnoreCase("Vigilancia")) {
            factoria = new CrearDronVigilancia();
        } else {
            throw new IllegalArgumentException("Tipo de dron no soportado: " + tipo);
        }

        return factoria.crearDrone();
    }

    /**
     * Construye un dron de tipo Vigilancia usando el Builder,
     * paso a paso, sin exponer el encadenamiento al cliente.
     */
    public Vigilancia crearDronConBuilder(
            int id,
            String serial,
            String modelo,
            double peso,
            boolean deteccionTermica) {

        builder = new Builder();

        return builder
                .id(id)
                .serial(serial)
                .modelo(modelo)
                .peso(peso)
                .deteccionTermica(deteccionTermica)
                .build();
    }

    /**
     * Clona un dron existente usando el Prototype.
     *
     * @param dron dron original a clonar
     * @return una copia independiente del dron
     */
    public Dron clonarDron(Dron dron) {

        prototype = new DronPrototype(dron);

        return prototype.clonar();
    }
}