/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import excepciones.DAOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import modelo.ConvocatoriaExamen;
import modelo.Dificultad;
import modelo.Enunciado;
import modelo.UnidadDidactica;

/**
 *
 * @author Alexander
 */
public class DaoimplementMySQL implements Dao {

    // SINGLETON PATTERN
    private static DaoimplementMySQL instance;
    private static final Object lock = new Object();
    // DATABASE CONNECTION
    private Connection con;
    private PreparedStatement stmt;

    // CONFIGURATION
    private ResourceBundle configFile;
    private String urlDB;
    private String userBD;
    private String passwordDB;

    // ARCHIVO PARA CONVOCATORIAS
    private static final String ARCHIVO_CONVOCATORIAS = "././resorces/convocatorias.dat";

    /**
     * Constructor privado - SINGLETON
     */
    private DaoimplementMySQL() {
        try {
            // Cargar configuración
            this.configFile = ResourceBundle.getBundle("config.database");
            this.urlDB = this.configFile.getString("Conn");
            this.userBD = this.configFile.getString("DBUser");
            this.passwordDB = this.configFile.getString("DBPass");

            LOGGER.info("DaoImplementacion Singleton inicializado");

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al cargar configuración, usando valores por defecto", e);
            // Valores por defecto
            this.urlDB = "jdbc:mysql://localhost:3306/examendb";
            this.userBD = "root";
            this.passwordDB = "abcd*1234";
        }
    }

    /**
     * Obtener instancia Singleton
     *
     * @return instancia cargada
     */
    public static DaoimplementMySQL getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new DaoimplementMySQL();
                }
            }
        }
        return instance;
    }

    // =================== GESTIÓN DE CONEXIONES ===================
    private void openConnection() throws SQLException {
        try {
            if (con == null || con.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String connectionUrl = urlDB;
                con = DriverManager.getConnection(connectionUrl, userBD, passwordDB);
                LOGGER.fine("Conexión establecida");
            }
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL no encontrado", e);
        }
    }

    private void closeConnection() throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
        if (con != null) {
            con.close();
        }
    }

    public Connection getConnection() throws SQLException {
        openConnection();
        return con;
    }

    @Override
    public boolean isConnected() {
        try {
            return con != null && !con.isClosed() && con.isValid(5);
        } catch (SQLException e) {
            return false;
        }
    }

    // =================== UNIDAD DIDÁCTICA ===================
    @Override
    public void insertarUnidadDidactica(UnidadDidactica unidad) throws DAOException {
        String sql = "INSERT INTO UnidadDidactica (acronimo, titulo, evaluacion, descripcion) VALUES (?, ?, ?, ?)";

        try {
            openConnection();
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, unidad.getAcronimo());
            stmt.setString(2, unidad.getTitulo());
            stmt.setString(3, unidad.getEvaluacion());
            stmt.setString(4, unidad.getDescripcion());

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new DAOException("Error al insertar la unidad didáctica");
            }

            // Obtener ID generado
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                unidad.setId(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            throw new DAOException("Error al insertar unidad didáctica: " + e.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                /* ignorar */ }
        }
    }

    @Override
    public void actualizarUnidadDidactica(UnidadDidactica unidad) throws DAOException {
        String sql = "UPDATE UnidadDidactica SET acronimo = ?, titulo = ?, evaluacion = ?, descripcion = ? WHERE id = ?";

        try {
            openConnection();
            stmt = con.prepareStatement(sql);

            stmt.setString(1, unidad.getAcronimo());
            stmt.setString(2, unidad.getTitulo());
            stmt.setString(3, unidad.getEvaluacion());
            stmt.setString(4, unidad.getDescripcion());
            stmt.setInt(5, unidad.getId());

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new DAOException("No se encontró la unidad didáctica con ID: " + unidad.getId());
            }

        } catch (SQLException e) {
            throw new DAOException("Error al actualizar unidad didáctica: " + e.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                /* ignorar */ }
        }
    }

    @Override
    public void eliminarUnidadDidactica(int id) throws DAOException {
        String sql = "DELETE FROM UnidadDidactica WHERE id = ?";

        try {
            openConnection();
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new DAOException("No se encontró la unidad didáctica con ID: " + id);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al eliminar unidad didáctica: " + e.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                /* ignorar */ }
        }
    }

    @Override
    public UnidadDidactica buscarUnidadDidacticaPorId(int id) throws DAOException {
        String sql = "SELECT * FROM UnidadDidactica WHERE id = ?";

        try {
            openConnection();
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapearUnidadDidactica(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new DAOException("Error al buscar unidad didáctica por ID: " + e.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                /* ignorar */ }
        }
    }

    @Override
    public UnidadDidactica buscarUnidadDidacticaPorAcronimo(String acronimo) throws DAOException {
        String sql = "SELECT * FROM UnidadDidactica WHERE acronimo = ?";

        try {
            openConnection();
            stmt = con.prepareStatement(sql);
            stmt.setString(1, acronimo);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapearUnidadDidactica(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new DAOException("Error al buscar unidad didáctica por acrónimo: " + e.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                /* ignorar */ }
        }
    }

    @Override
    public List<UnidadDidactica> buscarTodasLasUnidadesDidacticas() throws DAOException {
        String sql = "SELECT * FROM UnidadDidactica ORDER BY acronimo";
        List<UnidadDidactica> unidades = new ArrayList<UnidadDidactica>();

        try {
            openConnection();
            stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                unidades.add(mapearUnidadDidactica(rs));
            }

        } catch (SQLException e) {
            throw new DAOException("Error al buscar todas las unidades didácticas: " + e.getMessage(), e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                /* ignorar */ }
        }

        return unidades;
    }

    // =================== MÉTODOS AUXILIARES ===================
    private void guardarTodasLasConvocatorias(List<ConvocatoriaExamen> convocatorias) throws DAOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_CONVOCATORIAS))) {
            oos.writeObject(convocatorias);
        } catch (IOException e) {
            throw new DAOException("Error al guardar archivo de convocatorias: " + e.getMessage(), e);
        }
    }

    private UnidadDidactica mapearUnidadDidactica(ResultSet rs) throws SQLException {
        UnidadDidactica unidad = new UnidadDidactica();
        unidad.setId(rs.getInt("id"));
        unidad.setAcronimo(rs.getString("acronimo"));
        unidad.setTitulo(rs.getString("titulo"));
        unidad.setEvaluacion(rs.getString("evaluacion"));
        unidad.setDescripcion(rs.getString("descripcion"));
        return unidad;
    }

    private Enunciado mapearEnunciado(ResultSet rs) throws SQLException {
        Enunciado enunciado = new Enunciado();
        enunciado.setId(rs.getInt("id"));
        enunciado.setDescripcion(rs.getString("descripcion"));
        enunciado.setNivel(Dificultad.valueOf(rs.getString("nivel_dificultad")));
        enunciado.setDisponible(rs.getBoolean("disponible"));
        enunciado.setRuta(rs.getString("ruta"));
        return enunciado;
    }

    @Override
    public void probarConexion() throws DAOException {
        try {
            openConnection();
            if (isConnected()) {
                System.out.println("✅ Conexión DAO Singleton establecida correctamente");
                System.out.println("📊 Hash de instancia DAO: " + this.hashCode());
                System.out.println("🔗 URL: " + urlDB);
                System.out.println("👤 Usuario: " + userBD);
            }
            closeConnection();
        } catch (SQLException e) {
            throw new DAOException("Error al probar conexión: " + e.getMessage(), e);
        }
    }

    @Override
    public void cerrarRecursos() throws DAOException {
        try {
            closeConnection();
            LOGGER.info("Recursos DAO cerrados correctamente");
        } catch (SQLException e) {
            throw new DAOException("Error al cerrar recursos: " + e.getMessage(), e);
        }
    }

}
