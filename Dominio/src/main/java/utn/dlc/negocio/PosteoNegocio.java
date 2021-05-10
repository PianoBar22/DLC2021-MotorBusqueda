/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.negocio;

import utn.dlc.accesodatos.DBManagerVocabulario;
import utn.dlc.entidades.Documento;
import utn.dlc.produces.DBManagerProduces;

/**
 *
 * @author CC31899077
 */
public class PosteoNegocio {
    private DBManagerVocabulario dbVocabulario;

    public PosteoNegocio() {
        dbVocabulario = DBManagerProduces.create();
    }
    
    
    public void agregarPosteo(Documento doc, String palabra){
        
    }
}
