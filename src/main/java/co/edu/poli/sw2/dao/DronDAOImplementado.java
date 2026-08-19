package co.edu.poli.sw2.dao;

import co.edu.poli.servicios.db;
import co.edu.poli.sw2.modelo.Dron;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO para la entidad {@link Dron}.
 *
 * <p>
 * Esta clase permite realizar las operaciones CRUD sobre la tabla
 * {@code Drone} de la base de datos MySQL.
 * </p>
 *
 * <p>
 * Las operaciones disponibles son:
 * </p>
 *
 * <ul>
 * <li>Crear drones.</li>
 * <li>Listar drones.</li>
 * <li>Buscar drones por ID.</li>
 * <li>Actualizar drones.</li>
 * <li>Eliminar drones.</li>
 * </ul>
 *
 * @author Jsyh
 * @version 1.0
 */
public class DronDAOImplementado implements DronDAO {

    /**
     * Conexión utilizada para acceder a la base de datos.
     */
    private final Connection conexion;

    /**
     * Constructor de la implementación del DAO.
     *
     * <p>
     * Obtiene la conexión mediante la clase Singleton {@link db}.
     * </p>
     */
    public DronDAOImplementado() {
        conexion = db.getInstancia().getConexion();
    }

    /**
     * Crea un nuevo drone en la base de datos.
     *
     * <p>
     * El ID es generado automáticamente por MySQL debido a la propiedad
     * {@code AUTO_INCREMENT}.
     * </p>
     *
     * @param dron drone que se desea almacenar
     * @return {@code true} si el drone fue creado correctamente;
     *         {@code false} si ocurrió algún error
     */
    @Override
    public boolean crear(Dron dron) {

        if (dron == null) {
            return false;
        }

        if (conexion == null) {
            System.out.println("No existe conexión con la base de datos.");
            return false;
        }

        String sql = "INSERT INTO Drone (senal, modelo, peso) "
                   + "VALUES (?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setDouble(1, dron.getSenal());
            ps.setString(2, dron.getModelo());
            ps.setDouble(3, dron.getPeso());

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {

            System.out.println("Error al crear el drone.");
            System.out.println(e.getMessage());

            return false;
        }
    }

    /**
     * Obtiene todos los drones almacenados en la base de datos.
     *
     * @return lista de drones encontrados.
     *         Si ocurre un error, retorna una lista vacía.
     */
    @Override
    public List<Dron> listar() {

        List<Dron> drones = new ArrayList<>();

        if (conexion == null) {
            System.out.println("No existe conexión con la base de datos.");
            return drones;
        }

        String sql = "SELECT id, senal, modelo, peso FROM Drone";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Dron dron = new Dron();

                dron.setId(rs.getInt("id"));
                dron.setSenal(rs.getDouble("senal"));
                dron.setModelo(rs.getString("modelo"));
                dron.setPeso(rs.getDouble("peso"));

                drones.add(dron);
            }

        } catch (SQLException e) {

            System.out.println("Error al listar los drones.");
            System.out.println(e.getMessage());
        }

        return drones;
    }

    /**
     * Busca un drone por su identificador.
     *
     * @param id identificador del drone que se desea buscar
     * @return el drone encontrado o {@code null} si no existe
     */
    @Override
    public Dron buscarPorId(int id) {

        if (conexion == null) {
            System.out.println("No existe conexión con la base de datos.");
            return null;
        }

        String sql = "SELECT id, senal, modelo, peso "
                   + "FROM Drone WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Dron dron = new Dron();

                    dron.setId(rs.getInt("id"));
                    dron.setSenal(rs.getDouble("senal"));
                    dron.setModelo(rs.getString("modelo"));
                    dron.setPeso(rs.getDouble("peso"));

                    return dron;
                }
            }

        } catch (SQLException e) {

            System.out.println("Error al buscar el drone.");
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Actualiza los datos de un drone existente.
     *
     * <p>
     * El identificador del drone se utiliza para determinar
     * qué registro debe actualizarse.
     * </p>
     *
     * @param dron drone que contiene los datos actualizados
     * @return {@code true} si el drone fue actualizado correctamente;
     *         {@code false} si no existe o ocurrió un error
     */
    @Override
    public boolean actualizar(Dron dron) {

        if (dron == null) {
            return false;
        }

        if (conexion == null) {
            System.out.println("No existe conexión con la base de datos.");
            return false;
        }

        String sql = "UPDATE Drone "
                   + "SET senal = ?, modelo = ?, peso = ? "
                   + "WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setDouble(1, dron.getSenal());
            ps.setString(2, dron.getModelo());
            ps.setDouble(3, dron.getPeso());
            ps.setInt(4, dron.getId());

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {

            System.out.println("Error al actualizar el drone.");
            System.out.println(e.getMessage());

            return false;
        }
    }

    /**
     * Elimina un drone de la base de datos.
     *
     * <p>
     * La eliminación puede fallar si el drone está asociado a una misión,
     * debido a la restricción {@code ON DELETE RESTRICT} definida en la
     * tabla {@code Mision}.
     * </p>
     *
     * @param id identificador del drone que se desea eliminar
     * @return {@code true} si el drone fue eliminado correctamente;
     *         {@code false} si no existe o ocurrió un error
     */
    @Override
    public boolean eliminar(int id) {

        if (conexion == null) {
            System.out.println("No existe conexión con la base de datos.");
            return false;
        }

        String sql = "DELETE FROM Drone WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {

            System.out.println("Error al eliminar el drone.");
            System.out.println(e.getMessage());

            return false;
        }
    }
}