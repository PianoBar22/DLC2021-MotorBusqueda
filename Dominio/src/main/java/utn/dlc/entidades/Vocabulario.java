/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.entidades;

/**
 *
 * @author CC31899077
 */
public class Vocabulario {
    private Long id;
    private String palabra;
    private Integer cant_documentos;
    private Integer max_rf;

    public Vocabulario(String palabra, Integer cant_documentos, Integer max_rf) {
        this.palabra = palabra;
        this.cant_documentos = cant_documentos;
        this.max_rf = max_rf;
        this.id = 0L;
    }

    public Vocabulario(Long id, String palabra, Integer cant_documentos, Integer max_rf) {
        this.id = id;
        this.palabra = palabra;
        this.cant_documentos = cant_documentos;
        this.max_rf = max_rf;
    }
    
    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public Integer getCant_documentos() {
        return cant_documentos;
    }

    public void setCantDocumentos(Integer cant_documentos) {
        this.cant_documentos = cant_documentos;
    }

    public Integer getMax_rf() {
        return max_rf;
    }

    public void setMaxRf(Integer max_rf) {
        this.max_rf = max_rf;
    }

    @Override
    public String toString() {
        return "Vocabulario{" + "id=" + id + ", palabra=" + palabra + ", cant_documentos=" + cant_documentos + ", max_rf=" + max_rf + '}';
    }
    
    
}
