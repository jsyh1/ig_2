package co.edu.poli.sw2.dao;

import co.edu.poli.servicios.db;
import co.edu.poli.sw2.modelo.Agricultura;
import co.edu.poli.sw2.modelo.Dron;
import co.edu.poli.sw2.modelo.Vigilancia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del patrón DAO para gestionar las operaciones de persistencia
 * de los objetos {@link Dron}.
 *
 * <p>
 * Esta clase permite realizar las operaciones CRUD sobre los drones,
 * utilizando JDBC para comunicarse con la base de datos MySQL.
 * </p>
 *
 * <p>
 * Además, identifica los diferentes tipos de drones ({@link Agricultura}
 * y {@link Vigilancia}) y almacena sus atributos específicos en las tablas
 * correspondientes.
 * </p>
 *
 * @author Jsyh
 * @version 1.0
 */
public class DronDAOImplementado implements DronDAO {

    /**
     * Conexión utilizada para realizar las operaciones con la base de datos.
     */
    private final Connection conexion;

    /**
     * Constructor de la clase.
     *
     * <p>
     * Obtiene la conexión a la base de datos mediante la instancia Singleton
     * de la clase {@link db}.
     * </p>
     */
    public DronDAOImplementado() {
        conexion = db.getInstancia().getConexion();
    }

    // =========================================================
    // CREAR
    // =========================================================

    /**
     * Crea un nuevo dron en la base de datos.
     *
     * <p>
     * Primero almacena los datos generales del dron en la tabla
     * {@code Drone}. Posteriormente, dependiendo del tipo de dron,
     * almacena sus datos específicos en la tabla {@code Agricultura}
     * o {@code Vigilancia}.
     * </p>
     *
     * <p>
     * La operación utiliza una transacción para garantizar que todos
     * los datos se almacenen correctamente. Si ocurre algún error,
     * se realiza un {@code rollback}.
     * </p>
     *
     * @param dron objeto {@link Dron} que se desea almacenar
     * @return {@code true} si el dron fue creado correctamente;
     *         {@code false} si ocurre algún error o el objeto es inválido
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

        String sqlDron = """
                INSERT INTO Drone (serial, modelo, peso)
                VALUES (?, ?, ?)
                """;

        try {

            conexion.setAutoCommit(false);

            try (PreparedStatement ps = conexion.prepareStatement(
                    sqlDron,
                    Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, dron.getSerial());
                ps.setString(2, dron.getModelo());
                ps.setDouble(3, dron.getPeso());

                int filas = ps.executeUpdate();

                if (filas == 0) {
                    conexion.rollback();
                    return false;
                }

                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (!rs.next()) {
                        conexion.rollback();
                        return false;
                    }

                    int idGenerado = rs.getInt(1);

                    dron.setId(idGenerado);

                    if (dron instanceof Agricultura) {

                        Agricultura agricultura = (Agricultura) dron;

                        String sqlAgricultura = """
                                INSERT INTO Agricultura
                                (id, capacidad_tanque)
                                VALUES (?, ?)
                                """;

                        try (PreparedStatement psAgricultura =
                                     conexion.prepareStatement(sqlAgricultura)) {

                            psAgricultura.setInt(
                                    1,
                                    idGenerado
                            );

                            psAgricultura.setDouble(
                                    2,
                                    agricultura.getCapacidadTanque()
                            );

                            int resultado =
                                    psAgricultura.executeUpdate();

                            if (resultado == 0) {
                                conexion.rollback();
                                return false;
                            }
                        }

                    } else if (dron instanceof Vigilancia) {

                        Vigilancia vigilancia = (Vigilancia) dron;

                        String sqlVigilancia = """
                                INSERT INTO Vigilancia
                                (id, deteccion_termica)
                                VALUES (?, ?)
                                """;

                        try (PreparedStatement psVigilancia =
                                     conexion.prepareStatement(sqlVigilancia)) {

                            psVigilancia.setInt(
                                    1,
                                    idGenerado
                            );

                            psVigilancia.setBoolean(
                                    2,
                                    vigilancia.isDeteccionTermica()
                            );

                            int resultado =
                                    psVigilancia.executeUpdate();

                            if (resultado == 0) {
                                conexion.rollback();
                                return false;
                            }
                        }

                    } else {

                        conexion.rollback();
                        return false;
                    }

                    conexion.commit();

                    return true;
                }

            } catch (SQLException e) {

                conexion.rollback();
//arreglar esto
                System.out.println("Error al crear el drone.");
                System.out.println(e.getMessage());

                return false;

            } finally {

                conexion.setAutoCommit(true);
            }

        } catch (SQLException e) {

            System.out.println("Error en la transacción.");
            System.out.println(e.getMessage());

            return false;
        }
    }

    // =========================================================
    // LISTAR
    // =========================================================

    /**
     * Obtiene todos los drones registrados en la base de datos.
     *
     * <p>
     * Utiliza una consulta con {@code LEFT JOIN} para obtener los datos
     * generales del dron y determinar si pertenece al tipo Agricultura
     * o Vigilancia.
     * </p>
     *
     * @return lista de objetos {@link Dron} registrados; si no existe
     *         conexión o ocurre un error, devuelve una lista vacía
     */
    @Override
    public List<Dron> listar() {

        List<Dron> drones = new ArrayList<>();

        if (conexion == null) {
            System.out.println("No existe conexión con la base de datos.");
            return drones;
        }

        String sql = """
                SELECT
                    d.id,
                    d.serial,
                    d.modelo,
                    d.peso,

                    a.id AS agricultura_id,
                    a.capacidad_tanque,

                    v.id AS vigilancia_id,
                    v.deteccion_termica

                FROM Drone d

                LEFT JOIN Agricultura a
                    ON d.id = a.id

                LEFT JOIN Vigilancia v
                    ON d.id = v.id
                """;

        try (
                PreparedStatement ps =
                        conexion.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                Dron dron = null;

                if (rs.getObject("agricultura_id") != null) {

                    Agricultura agricultura =
                            new Agricultura();

                    agricultura.setCapacidadTanque(
                            rs.getDouble("capacidad_tanque")
                    );

                    dron = agricultura;
                }

                else if (rs.getObject("vigilancia_id") != null) {

                    Vigilancia vigilancia =
                            new Vigilancia();

                    vigilancia.setDeteccionTermica(
                            rs.getBoolean("deteccion_termica")
                    );

                    dron = vigilancia;
                }

                if (dron != null) {

                    dron.setId(
                            rs.getInt("id")
                    );

                    dron.setSerial(
                            rs.getString("serial")
                    );

                    dron.setModelo(
                            rs.getString("modelo")
                    );

                    dron.setPeso(
                            rs.getDouble("peso")
                    );

                    drones.add(dron);
                }
            }

        } catch (SQLException e) {

            System.out.println("Error al listar los drones.");
            System.out.println(e.getMessage());
        }

        return drones;
    }

    // =========================================================
    // BUSCAR POR ID
    // =========================================================

    /**
     * Busca un dron específico utilizando su identificador.
     *
     * <p>
     * La consulta obtiene los datos generales y determina el tipo de dron
     * mediante las tablas {@code Agricultura} y {@code Vigilancia}.
     * </p>
     *
     * @param id identificador del dron que se desea buscar
     * @return objeto {@link Dron} encontrado o {@code null} si no existe
     *         o ocurre un error
     */
    @Override
    public Dron buscarPorId(int id) {

        if (conexion == null) {
            System.out.println("No existe conexión con la base de datos.");
            return null;
        }

        String sql = """
                SELECT
                    d.id,
                    d.serial,
                    d.modelo,
                    d.peso,

                    a.idn AS agricultura_id,
                    a.capacidad_tanque,

                    v.id AS vigilancia_id,
                    v.deteccion_termica

                FROM Drone d

                LEFT JOIN Agricultura a
                    ON d.id = a.id

                LEFT JOIN Vigilancia v
                    ON d.id = v.id

                WHERE d.id = ?
                """;

        try (PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Dron dron = null;

                    if (rs.getObject("agricultura_id") != null) {

                        Agricultura agricultura =
                                new Agricultura();

                        agricultura.setCapacidadTanque(
                                rs.getDouble("capacidad_tanque")
                        );

                        dron = agricultura;
                    }

                    else if (rs.getObject("vigilancia_id") != null) {

                        Vigilancia vigilancia =
                                new Vigilancia();

                        vigilancia.setDeteccionTermica(
                                rs.getBoolean("deteccion_termica")
                        );

                        dron = vigilancia;
                    }

                    if (dron != null) {

                        dron.setId(
                                rs.getInt("id")
                        );

                        dron.setSerial(
                                rs.getString("serial")
                        );

                        dron.setModelo(
                                rs.getString("modelo")
                        );

                        dron.setPeso(
                                rs.getDouble("peso")
                        );

                        return dron;
                    }
                }
            }

        } catch (SQLException e) {

            System.out.println("Error al buscar el drone.");
            System.out.println(e.getMessage());
        }

        return null;
    }

    // =========================================================
    // ACTUALIZAR
    // =========================================================

    /**
     * Actualiza los datos de un dron existente.
     *
     * <p>
     * Primero actualiza los datos generales en la tabla {@code Drone}.
     * Posteriormente actualiza los datos específicos en la tabla
     * correspondiente según el tipo de dron.
     * </p>
     *
     * <p>
     * La operación se realiza dentro de una transacción para garantizar
     * la integridad de los datos.
     * </p>
     *
     * @param dron objeto {@link Dron} con los datos actualizados
     * @return {@code true} si la actualización fue exitosa;
     *         {@code false} si ocurre algún error o el objeto es inválido
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

        try {

            conexion.setAutoCommit(false);

            String sqlDron = """
                    UPDATE Drone
                    SET serial = ?, modelo = ?, peso = ?
                    WHERE id = ?
                    """;

            try (PreparedStatement ps =
                         conexion.prepareStatement(sqlDron)) {

                ps.setString(
                        1,
                        dron.getSerial()
                );

                ps.setString(
                        2,
                        dron.getModelo()
                );

                ps.setDouble(
                        3,
                        dron.getPeso()
                );

                ps.setInt(
                        4,
                        dron.getId()
                );

                int filas = ps.executeUpdate();

                if (filas == 0) {
                    conexion.rollback();
                    return false;
                }
            }

            if (dron instanceof Agricultura) {

                Agricultura agricultura =
                        (Agricultura) dron;

                String sqlAgricultura = """
                        UPDATE Agricultura
                        SET capacidad_tanque = ?
                        WHERE id = ?
                        """;

                try (PreparedStatement ps =
                             conexion.prepareStatement(sqlAgricultura)) {

                    ps.setDouble(
                            1,
                            agricultura.getCapacidadTanque()
                    );

                    ps.setInt(
                            2,
                            dron.getId()
                    );

                    ps.executeUpdate();
                }

            } else if (dron instanceof Vigilancia) {

                Vigilancia vigilancia =
                        (Vigilancia) dron;

                String sqlVigilancia = """
                        UPDATE Vigilancia
                        SET deteccion_termica = ?
                        WHERE id = ?
                        """;

                try (PreparedStatement ps =
                             conexion.prepareStatement(sqlVigilancia)) {

                    ps.setBoolean(
                            1,
                            vigilancia.isDeteccionTermica()
                    );

                    ps.setInt(
                            2,
                            dron.getId()
                    );

                    ps.executeUpdate();
                }
            }

            conexion.commit();

            return true;

        } catch (SQLException e) {

            try {
                conexion.rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            System.out.println("Error al actualizar.");
            System.out.println(e.getMessage());

            return false;

        } finally {

            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // =========================================================
    // ELIMINAR
    // =========================================================

    /**
     * Elimina un dron de la base de datos.
     *
     * <p>
     * Primero elimina los registros relacionados en las tablas
     * {@code Agricultura} y {@code Vigilancia} y posteriormente elimina
     * el registro correspondiente de la tabla {@code Drone}.
     * </p>
     *
     * <p>
     * La operación se ejecuta dentro de una transacción para mantener
     * la integridad referencial de la información.
     * </p>
     *
     * @param id identificador del dron que se desea eliminar
     * @return {@code true} si el dron fue eliminado correctamente;
     *         {@code false} si no existe o ocurre un error
     */
    @Override
    public boolean eliminar(int id) {

        if (conexion == null) {
            System.out.println("No existe conexión con la base de datos.");
            return false;
        }

        try {

            conexion.setAutoCommit(false);

            String sqlAgricultura = """
                    DELETE FROM Agricultura
                    WHERE id = ?
                    """;

            try (PreparedStatement ps =
                         conexion.prepareStatement(sqlAgricultura)) {

                ps.setInt(1, id);
                ps.executeUpdate();
            }

            String sqlVigilancia = """
                    DELETE FROM Vigilancia
                    WHERE id = ?
                    """;

            try (PreparedStatement ps =
                         conexion.prepareStatement(sqlVigilancia)) {

                ps.setInt(1, id);
                ps.executeUpdate();
            }

            String sqlDron = """
                    DELETE FROM Drone
                    WHERE id = ?
                    """;

            try (PreparedStatement ps =
                         conexion.prepareStatement(sqlDron)) {

                ps.setInt(1, id);

                int filas = ps.executeUpdate();

                if (filas == 0) {
                    conexion.rollback();
                    return false;
                }
            }

            conexion.commit();

            return true;

        } catch (SQLException e) {

            try {
                conexion.rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            System.out.println("Error al eliminar.");
            System.out.println(e.getMessage());

            return false;

        } finally {

            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}