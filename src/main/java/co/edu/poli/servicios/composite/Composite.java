package co.edu.poli.servicios.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación compuesta del patrón Composite.
 *
 * <p>Un objeto {@code Composite} puede contener una colección de objetos
 * que implementan {@link Component}, permitiendo tratar componentes
 * individuales y grupos de componentes mediante una interfaz común.</p>
 */
public class Composite implements Component {

    /**
     * Lista de componentes hijos contenidos dentro del Composite.
     */
    private List<Component> hijos;

    /**
     * Crea un Composite con una lista vacía de componentes hijos.
     */
    public Composite() {

        hijos = new ArrayList<>();
    }

    /**
     * Representa la operación de agregar un componente al Composite.
     *
     * @param componente nombre o descripción del componente que se desea agregar
     * @return mensaje indicando que el componente fue agregado
     */
    public String agregar(String componente) {

        return "Componente agregado: " + componente;
    }

    /**
     * Representa la operación de eliminar un componente del Composite.
     *
     * @param componente nombre o descripción del componente que se desea eliminar
     * @return mensaje indicando que el componente fue eliminado
     */
    public String eliminar(String componente) {

        return "Componente eliminado: " + componente;
    }

    /**
     * Muestra la información de todos los componentes hijos.
     *
     * <p>El método recorre la lista de componentes y concatena
     * el resultado proporcionado por cada uno.</p>
     *
     * @return cadena con la información de los componentes hijos
     */
    @Override
    public String mostrar() {

        String resultado = "";

        for (Component hijo : hijos) {

            resultado += hijo.mostrar() + "\n";
        }

        return resultado;
    }
}