package co.edu.poli.servicios.proxy;

import co.edu.poli.sw2.dao.DronDAO;
import co.edu.poli.sw2.dao.DronDAOImplementado;

public class ServicioEliminarReal implements ServicioEliminar {

    private final DronDAO dronDAO;

    public ServicioEliminarReal() {
        this.dronDAO = new DronDAOImplementado();
    }

    @Override
    public boolean eliminar(int id) {
        return dronDAO.eliminar(id);
    }
}