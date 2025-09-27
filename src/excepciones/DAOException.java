/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Alexander
 */
public class DAOException extends Exception{

    public DAOException(String error_al_insertar_la_unidad_didáctica) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public DAOException(String string, SQLException e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public DAOException(String string, IOException e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
