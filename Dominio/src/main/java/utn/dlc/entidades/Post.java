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
public class Post {
    private long id;
    private Vocabulario vocabulario;
    private Documento documento;
    private long frecuencia;
    private float indiceRelevancia;

    public Post(Vocabulario vocabulario, Documento documento, long frecuencia) {
        this.vocabulario = vocabulario;
        this.documento = documento;
        this.frecuencia = frecuencia;
        this.id = 0L;
        this.indiceRelevancia = 0F;
    }
    
    public Post(long id, Vocabulario vocabulario, Documento documento, long frecuencia) {
        this.vocabulario = vocabulario;
        this.documento = documento;
        this.frecuencia = frecuencia;
        this.id = id;
        this.indiceRelevancia = 0F;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public Vocabulario getVocabulario() {
        return vocabulario;
    }

    public void setVocabulario(Vocabulario vocabulario) {
        this.vocabulario = vocabulario;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public long getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(long frecuencia) {
        this.frecuencia = frecuencia;
    }

    public float getIndiceRelevancia() {
        return indiceRelevancia;
    }

    public void setIndiceRelevancia(float indiceRelevancia) {
        this.indiceRelevancia = indiceRelevancia;
    }
}
