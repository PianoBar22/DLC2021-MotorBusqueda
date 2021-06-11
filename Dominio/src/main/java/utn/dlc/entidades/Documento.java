/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.entidades;

import java.util.Objects;

/**
 *
 * @author CC31899077
 */
public class Documento implements Comparable<Documento>{
    
    private String path;
    private Long id;
    private float indiceRelevancia;

    public Documento(long id, String path) {
        this.path = path;
        this.id = id;
    }
    
    public Documento(String path) {
        this.path = path;
        this.id = 0L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the value of path
     *
     * @return the value of path
     */
    public String getPath() {
        return path;
    }

    /**
     * Set the value of path
     *
     * @param path new value of path
     */
    public void setPath(String path) {
        this.path = path;
    }

    public float getIndiceRelevancia() {
        return indiceRelevancia;
    }

    public void setIndiceRelevancia(float indiceRelevancia) {
        this.indiceRelevancia = indiceRelevancia;
    }

    @Override
    public int compareTo(Documento o) {
        return Float.compare(this.getIndiceRelevancia(), o.getIndiceRelevancia());
    }

    @Override
    public String toString() {
        return "Documento nro " + this.getId() + ", Path: " + this.getPath() + ", Indice Relevancia: " + this.getIndiceRelevancia();
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(this.getId(), ((Documento)obj).getId());
    }

    
}
