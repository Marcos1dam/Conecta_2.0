/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author juanm
 */

public class UnidadDidactica implements Serializable {

    private int id;
    private String acronimo;
    private String titulo;
    private String evaluacion;
    private String descripcion;
    private List<Enunciado> enunciados;


    public UnidadDidactica() {
        this.enunciados = new ArrayList<>();
    }

    public UnidadDidactica(String acronimo, String titulo, String evaluacion, String descripcion) {
        this();
        this.acronimo = acronimo;
        this.titulo = titulo;
        this.evaluacion = evaluacion;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAcronimo() {
        return acronimo;
    }

    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(String evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Enunciado> getEnunciados() {
        return enunciados;
    }

    public void setEnunciados(List<Enunciado> enunciados) {
        this.enunciados = enunciados;

    }

    @Override
    public String toString() {

        return "UnidadDidactica{"
                + "id=" + id
                + ", acronimo='" + acronimo + '\''
                + ", titulo='" + titulo + '\''
                + ", evaluacion='" + evaluacion + '\''
                + ", descripcion='" + descripcion + '\''
                + '}';

    }
}
