/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.Dao;
import dao.DaoImplementacion;
import java.util.logging.Logger;

/**
 *
 * @author juanm
 */
public class ExamenService {
    private static final Logger LOGGER = Logger.getLogger(ExamenService.class.getName());
    
    // SINGLETON PATTERN IMPLEMENTATION
    private static ExamenService instance;
    private static final Object lock = new Object();
    
    // DEPENDENCIA CON LA CAPA DAO (también Singleton)
    private final Dao dao;
    
    /**
     * Constructor privado - CLAVE DEL SINGLETON
     * Inicializa la dependencia con el DAO
     */
    private ExamenService() {
        this.dao = DaoImplementacion.getInstance(); // DAO Singleton
        LOGGER.info("ExamenService Singleton inicializado");
    }
    
    /**
     * Método para obtener la única instancia (Thread-Safe)
     * LAZY INITIALIZATION + DOUBLE-CHECKED LOCKING
     */
    public static ExamenService getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ExamenService();
                }
            }
        }
        return instance;
    }
    
    
}
