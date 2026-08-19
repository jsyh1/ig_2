package co.edu.poli.sw2.servicios;

import co.edu.poli.sw2.modelo.Dron;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación temporal del DAO para la entidad Dron.
 *
 * <p>
 * Los datos se almacenan temporalmente en una lista en memoria
 * mientras la conexión con la base de datos no esté disponible.
 * </p>
 *
 * @author Jsyh
 * @version 1.0
 */
public class DronDAOImplementado implements DronDAO {

    /**
     * Lista local donde se almacenan los drones.
     */
    private final List<Dron> drones = new ArrayList<>();

    /**
     * Crea un nuevo drone y lo almacena en la lista local.
     *
     * @param dron drone que se desea crear
     * @return true si el drone fue creado correctamente
     */
    @Override
    public boolean crear(Dron dron) {

        if (dron == null) {
            return false;
        }

        if (buscarPorId(dron.getId()) != null) {
            return false;
        }

        drones.add(dron);

        return true;
    }

    /**
     * Obtiene todos los drones almacenados localmente.
     *
     * @return lista con todos los drones
     */
    @Override
    public List<Dron> listar() {

        return new ArrayList<>(drones);
    }

    /**
     * Busca un drone por su identificador.
     *
     * @param id identificador del drone
     * @return drone encontrado o null si no existe
     */
    @Override
    public Dron buscarPorId(int id) {

        for (Dron dron : drones) {

            if (dron.getId() == id) {
                return dron;
            }
        }

        return null;
    }

    /**
     * Actualiza los datos de un drone.
     *
     * @param dron drone con los datos actualizados
     * @return true si se actualizó correctamente
     */
    @Override
    public boolean actualizar(Dron dron) {

        if (dron == null) {
            return false;
        }

        Dron dronExistente = buscarPorId(dron.getId());

        if (dronExistente == null) {
            return false;
        }

        dronExistente.setSenal(dron.getSenal());
        dronExistente.setModelo(dron.getModelo());
        dronExistente.setPeso(dron.getPeso());

        return true;
    }

    /**
     * Elimina un drone por su identificador.
     *
     * @param id identificador del drone
     * @return true si el drone fue eliminado
     */
    @Override
    public boolean eliminar(int id) {

        Dron dron = buscarPorId(id);

        if (dron == null) {
            return false;
        }

        drones.remove(dron);

        return true;
    }
}