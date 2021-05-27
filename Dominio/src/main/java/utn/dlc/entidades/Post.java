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
    private Vocabulario vocabulario;
    private Documento documento;
    private long frecuencia;

    public Post(Vocabulario vocabulario, Documento documento, long frecuencia) {
        this.vocabulario = vocabulario;
        this.documento = documento;
        this.frecuencia = frecuencia;
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
    
    
}
