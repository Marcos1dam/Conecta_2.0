/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author juanm
 */
public interface Dao{
    
    
    
    // =================== OPERACIONES DE GESTIÓN ===================
    
    /**
     * Probar la conexión a la base de datos
     * @throws DAOException Si no se puede establecer conexión
     */
    void probarConexion() throws DAOException;
    
    /**
     * Cerrar todos los recursos de conexión
     * Importante para el patrón Singleton al finalizar la aplicación
     * @throws DAOException Si ocurre un error al cerrar recursos
     */
    void cerrarRecursos() throws DAOException;
    
    /**
     * Verificar si hay una conexión activa
     * @return true si hay conexión activa, false en caso contrario
     */
    boolean isConnected();
    
    /**
     * Obtener estadísticas del DAO (para debugging y monitoreo)
     * @return String con información del estado del DAO
     */
    default String obtenerEstadisticas() {
        return "DAO Singleton - Estado: " + (isConnected() ? "Conectado" : "Desconectado");
    }
}
