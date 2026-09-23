package co.edu.poli.servicios.facade;

import co.edu.poli.servicios.builder.Builder;
import co.edu.poli.servicios.factoria.CrearDronAgricultura;
import co.edu.poli.servicios.factoria.CrearDronVigilancia;
import co.edu.poli.servicios.factoria.FactoriaDrones;
import co.edu.poli.servicios.prototype.Prototype;
import co.edu.poli.servicios.prototype.DronPrototype;
import co.edu.poli.sw2.modelo.Dron;
import co.edu.poli.sw2.modelo.Vigilancia;

/**
 * Fachada que centraliza diferentes mecanismos de creación de drones.
 *
 * <p>Esta clase implementa el patrón Facade y proporciona una interfaz
 * simplificada para que el cliente pueda utilizar Factory Method,
 * Builder y Prototype sin interactuar directamente con las clases
 * que implementan cada mecanismo.</p>
 */
public class DronFacade {

    /**
     * Factoria utilizada para crear drones mediante Factory Method.
     */
    private FactoriaDrones factoria;

    /**
     * Builder utilizado para construir drones paso a paso.
     */
    private Builder builder;

    /**
     * Prototype utilizado para clonar drones existentes.
     */
    private Prototype<Dron> prototype;

    /**
     * Crea un dron utilizando el patrón Factory Method.
     *
     * <p>La factoría utilizada depende del tipo de dron recibido.</p>
     *
     * @param tipo tipo de dron que se desea crear.
     *             Puede ser {@code "Agricultura"} o {@code "Vigilancia"}.
     * @return dron creado por la factoría correspondiente
     * @throws IllegalArgumentException si el tipo de dron no está soportado
     */
    public Dron crearDronDesdeFactory(String tipo) {

        if (tipo.equalsIgnoreCase("Agricultura")) {

            factoria = new CrearDronAgricultura();

        } else if (tipo.equalsIgnoreCase("Vigilancia")) {

            factoria = new CrearDronVigilancia();

        } else {

            throw new IllegalArgumentException(
                "Tipo de dron no soportado: " + tipo
            );
        }

        return factoria.crearDrone();
    }

    /**
     * Construye un dron de vigilancia utilizando el patrón Builder.
     *
     * <p>La fachada encapsula el proceso de construcción paso a paso,
     * evitando que el cliente tenga que conocer directamente los métodos
     * utilizados por el Builder.</p>
     *
     * @param id identificador del dron
     * @param serial número serial del dron
     * @param modelo modelo del dron
     * @param peso peso del dron
     * @param deteccionTermica indica si el dron cuenta con detección térmica
     * @return dron de vigilancia completamente construido
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
     * Clona un dron utilizando el patrón Prototype.
     *
     * <p>Se crea un objeto {@link DronPrototype} a partir del dron original
     * y posteriormente se solicita una copia mediante {@code clonar()}.</p>
     *
     * @param dron dron original que será clonado
     * @return copia del dron original
     */
    public Dron clonarDron(Dron dron) {

        prototype = new DronPrototype(dron);

        return prototype.clonar();
    }
}