package co.edu.poli.sw2.servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase Singleton encargada de gestionar la conexión
 * con la base de datos MySQL.
 *
 * <p>El patrón Singleton garantiza que solamente exista
 * una instancia de esta clase durante la ejecución del programa.</p>
 *
 * @author Jsyh
 * @version 1.0
 */
public class db {

    private static db instancia;

    private static final String URL =
            "jdbc:mysql://localhost:3306/drone_db";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    private Connection conexion;

    /**
     * Constructor privado para impedir que otras clases
     * creen directamente objetos de tipo {@code db}.
     */
    private db() {
        conectar();
    }

    /**
     * Obtiene la única instancia de la clase {@code db}.
     *
     * @return instancia única de {@code db}
     */
    public static db getInstancia() {

        if (instancia == null) {
            instancia = new db();
        }

        return instancia;
    }

    /**
     * Establece la conexión con la base de datos.
     *
     * @return conexión establecida o {@code null} si ocurre un error
     */
    private Connection conectar() {

        try {
            conexion = DriverManager.getConnection(
                    URL,
                    USUARIO,
                    CLAVE
            );

            System.out.println("Conexion exitosa a la base de datos");

        } catch (SQLException e) {

            System.out.println(
                    "Error al conectar a la base de datos"
            );

            System.out.println(e.getMessage());
        }

        return conexion;
    }

    /**
     * Obtiene la conexión actual con la base de datos.
     *
     * @return objeto {@link Connection} utilizado para acceder
     * a la base de datos
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * Cierra la conexión con la base de datos.
     */
    public void cerrarConexion() {

        try {

            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println(
                        "Conexion cerrada correctamente"
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al cerrar la conexion"
            );

            System.out.println(e.getMessage());
        }
    }
}