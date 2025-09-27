/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import excepciones.DAOException;
import java.util.List;
import modelo.UnidadDidactica;

/**
 *
 * @author juanm
 */
public interface Dao {

    // =================== OPERACIONES UNIDAD DIDÁCTICA ===================
    /**
     * Caso de uso 1: Crear una unidad didáctica
     *
     * @param unidad La unidad didáctica a insertar
     * @throws DAOException Si ocurre un error en la operación
     */
    void insertarUnidadDidactica(UnidadDidactica unidad) throws DAOException;

    /**
     * Actualizar una unidad didáctica existente
     *
     * @param unidad La unidad didáctica con los nuevos datos
     * @throws DAOException Si no se encuentra la unidad o hay error
     */
    void actualizarUnidadDidactica(UnidadDidactica unidad) throws DAOException;

    /**
     * Eliminar una unidad didáctica por ID
     *
     * @param id ID de la unidad didáctica a eliminar
     * @throws DAOException Si no se encuentra la unidad o hay error
     */
    void eliminarUnidadDidactica(int id) throws DAOException;

    /**
     * Buscar una unidad didáctica por su ID
     *
     * @param id ID de la unidad didáctica
     * @return La unidad didáctica encontrada o null si no existe
     * @throws DAOException Si ocurre un error en la búsqueda
     */
    UnidadDidactica buscarUnidadDidacticaPorId(int id) throws DAOException;

    /**
     * Buscar una unidad didáctica por su acrónimo
     *
     * @param acronimo Acrónimo de la unidad didáctica
     * @return La unidad didáctica encontrada o null si no existe
     * @throws DAOException Si ocurre un error en la búsqueda
     */
    UnidadDidactica buscarUnidadDidacticaPorAcronimo(String acronimo) throws DAOException;

    /**
     * Obtener todas las unidades didácticas
     *
     * @return Lista con todas las unidades didácticas
     * @throws DAOException Si ocurre un error en la consulta
     */
    List<UnidadDidactica> buscarTodasLasUnidadesDidacticas() throws DAOException;

    
    
    
    
    // =================== OPERACIONES DE GESTIÓN ===================
    /**
     * Probar la conexión a la base de datos
     *
     * @throws DAOException Si no se puede establecer conexión
     */
    void probarConexion() throws DAOException;

    /**
     * Cerrar todos los recursos de conexión Importante para el patrón Singleton
     * al finalizar la aplicación
     *
     * @throws DAOException Si ocurre un error al cerrar recursos
     */
    void cerrarRecursos() throws DAOException;

    /**
     * Verificar si hay una conexión activa
     *
     * @return true si hay conexión activa, false en caso contrario
     */
    boolean isConnected();

    /**
     * Obtener estadísticas del DAO (para debugging y monitoreo)
     *
     * @return String con información del estado del DAO
     */
    default String obtenerEstadisticas() {
        return "DAO Singleton - Estado: " + (isConnected() ? "Conectado" : "Desconectado");
    }

    // =================== OPERACIONES TRANSACCIONALES ===================
    /**
     * Iniciar una transacción de base de datos Útil para operaciones que
     * requieren consistencia (ej: crear enunciado + asociaciones)
     *
     * @throws DAOException Si no se puede iniciar la transacción
     */
    default void iniciarTransaccion() throws DAOException {
        // Implementación por defecto vacía
        // Las implementaciones concretas pueden sobrescribir
    }

    /**
     * Confirmar (commit) una transacción
     *
     * @throws DAOException Si no se puede confirmar la transacción
     */
    default void confirmarTransaccion() throws DAOException {
        // Implementación por defecto vacía
    }

    /**
     * Deshacer (rollback) una transacción
     *
     * @throws DAOException Si no se puede deshacer la transacción
     */
    default void deshacerTransaccion() throws DAOException {
        // Implementación por defecto vacía
    }
}
