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

public class DronDAOImplementado implements DronDAO {

    private final Connection conexion;

    public DronDAOImplementado() {
        conexion = db.getInstancia().getConexion();
    }


    // =========================================================
    // CREAR
    // =========================================================

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

            // Iniciamos transacción
            conexion.setAutoCommit(false);

            try (PreparedStatement ps = conexion.prepareStatement(
                    sqlDron,
                    Statement.RETURN_GENERATED_KEYS)) {

                // =================================================
                // 1. GUARDAR DATOS GENERALES EN DRONE
                // =================================================

                ps.setString(1, dron.getSerial());
                ps.setString(2, dron.getModelo());
                ps.setDouble(3, dron.getPeso());

                int filas = ps.executeUpdate();

                if (filas == 0) {
                    conexion.rollback();
                    return false;
                }


                // =================================================
                // 2. OBTENER ID GENERADO
                // =================================================

                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (!rs.next()) {
                        conexion.rollback();
                        return false;
                    }

                    int idGenerado = rs.getInt(1);

                    // Guardamos el ID también dentro del objeto Java
                    dron.setId(idGenerado);


                    // =================================================
                    // 3. SI EL DRON ES DE AGRICULTURA
                    // =================================================

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


                    // =================================================
                    // 4. SI EL DRON ES DE VIGILANCIA
                    // =================================================

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

                        // Si no es Agricultura ni Vigilancia
                        conexion.rollback();
                        return false;
                    }

                    // =================================================
                    // 5. TODO SALIÓ BIEN
                    // =================================================

                    conexion.commit();

                    return true;
                }

            } catch (SQLException e) {

                conexion.rollback();

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


                // =============================================
                // ES AGRICULTURA
                // =============================================

                if (rs.getObject("agricultura_id") != null) {

                    Agricultura agricultura =
                            new Agricultura();

                    agricultura.setCapacidadTanque(
                            rs.getDouble("capacidad_tanque")
                    );

                    dron = agricultura;
                }


                // =============================================
                // ES VIGILANCIA
                // =============================================

                else if (rs.getObject("vigilancia_id") != null) {

                    Vigilancia vigilancia =
                            new Vigilancia();

                    vigilancia.setDeteccionTermica(
                            rs.getBoolean("deteccion_termica")
                    );

                    dron = vigilancia;
                }


                // =============================================
                // DATOS GENERALES
                // =============================================

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


                    // =========================================
                    // AGRICULTURA
                    // =========================================

                    if (rs.getObject("agricultura_id") != null) {

                        Agricultura agricultura =
                                new Agricultura();

                        agricultura.setCapacidadTanque(
                                rs.getDouble("capacidad_tanque")
                        );

                        dron = agricultura;
                    }


                    // =========================================
                    // VIGILANCIA
                    // =========================================

                    else if (rs.getObject("vigilancia_id") != null) {

                        Vigilancia vigilancia =
                                new Vigilancia();

                        vigilancia.setDeteccionTermica(
                                rs.getBoolean("deteccion_termica")
                        );

                        dron = vigilancia;
                    }


                    // =========================================
                    // DATOS GENERALES
                    // =========================================

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


            // =================================================
            // 1. ACTUALIZAR DATOS GENERALES
            // =================================================

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


            // =================================================
            // 2. ACTUALIZAR AGRICULTURA
            // =================================================

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


            // =================================================
            // 3. ACTUALIZAR VIGILANCIA
            // =================================================

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

    @Override
    public boolean eliminar(int id) {

        if (conexion == null) {
            System.out.println("No existe conexión con la base de datos.");
            return false;
        }

        try {

            conexion.setAutoCommit(false);


            // Primero eliminamos Agricultura

            String sqlAgricultura = """
                    DELETE FROM Agricultura
                    WHERE id = ?
                    """;

            try (PreparedStatement ps =
                         conexion.prepareStatement(sqlAgricultura)) {

                ps.setInt(1, id);
                ps.executeUpdate();
            }


            // Luego eliminamos Vigilancia

            String sqlVigilancia = """
                    DELETE FROM Vigilancia
                    WHERE id = ?
                    """;

            try (PreparedStatement ps =
                         conexion.prepareStatement(sqlVigilancia)) {

                ps.setInt(1, id);
                ps.executeUpdate();
            }


            // Finalmente eliminamos el Drone

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