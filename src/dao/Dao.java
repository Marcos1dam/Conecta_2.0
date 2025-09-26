/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import excepciones.DAOException;
import modelo.ConvocatoriaExamen;
import modelo.Enunciado;
import modelo.UnidadDidactica;

/**
 *
 * @author juanm
 */
public interface Dao {

    public void cerrarRecursos();

    public UnidadDidactica buscarUnidadDidacticaPorAcronimo(String acronimo)throws DAOException;

    public void insertarUnidadDidactica(UnidadDidactica unidad)throws DAOException;

    public UnidadDidactica buscarUnidadDidacticaPorId(Integer unidadId)throws DAOException;

    public ConvocatoriaExamen buscarConvocatoriaPorNombre(String trim)throws DAOException;

    public void insertarEnunciado(Enunciado enunciado)throws DAOException;

    public void asociarEnunciadoConUnidadDidactica(int id, Integer unidadId)throws DAOException;

    public void asignarEnunciadoAConvocatoria(String trim, int id)throws DAOException;

    public Enunciado buscarEnunciadoPorId(int enunciadoId)throws DAOException;
  
}
