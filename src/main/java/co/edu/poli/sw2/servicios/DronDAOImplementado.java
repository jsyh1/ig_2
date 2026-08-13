package co.edu.poli.sw2.servicios;

import co.edu.poli.sw2.modelo.Dron;
import java.util.List;

/**
 * Implementación del DAO para Drone.
 *
 * @author Jsyh
 * @version 1.0
 */
public class DronDAOImplementado implements DronDAO {

    @Override
    public boolean crear(Dron dron) {
        return false;
    }

    @Override
    public List<Dron> listar() {
        return null;
    }

    @Override
    public Dron buscarPorId(int id) {
        return null;
    }

    @Override
    public boolean actualizar(Dron dron) {
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        return false;
    }
}