/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.buscador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.Tuple;
import utn.dlc.accesodatos.DBManagerDocumento;
import utn.dlc.accesodatos.DBManagerPost;
import utn.dlc.accesodatos.DBManagerVocabulario;
import utn.dlc.entidades.Documento;
import utn.dlc.entidades.Post;
import utn.dlc.entidades.Vocabulario;
import utn.dlc.negocio.PosteoNegocio;
import utn.dlc.produces.DBManagerProduces;
import utn.dlc.soporte.Heap;
import utn.dlc.soporte.QuickSort;

/**
 *
 * @author CC31899077
 */
public class Buscador {
    private DBManagerVocabulario dbVocabulario;
    private DBManagerDocumento dbDocumento;
    private DBManagerPost dbPost;
    
    public Buscador() {
        try {
            dbVocabulario = DBManagerProduces.createVocabulario();
            dbPost = DBManagerProduces.createPost();
            this.dbDocumento = DBManagerProduces.createDocumento();
        } catch (Exception ex) {
            Logger.getLogger(PosteoNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Documento> Buscar(ArrayList<String> palabras) throws Exception{
        HashMap<Long, Integer> retFactores = new HashMap<>();
        HashMap<Long, Documento> retDocumentos = new HashMap<>();
        long cantDocumentos = this.dbDocumento.getCantDocumentos();
        QuickSort<Documento> sortDoc = new QuickSort<>();
        
        ArrayList<Vocabulario> listVoc = this.dbVocabulario.searchVocabulario(palabras);
        
        for (Vocabulario voc: listVoc){
            ArrayList<Post> listPost = this.dbPost.searchByVocabulario(voc);
            
            for(Post post : listPost){
                float ir = post.getDocumento().getIndiceRelevancia();
                Integer factor = 0;
                
                if(retFactores.containsKey(post.getDocumento().getId())){
                    factor = retFactores.get(post.getDocumento().getId()) + 1;
                    ir = retDocumentos.get(post.getDocumento().getId()).getIndiceRelevancia();
                }
                ir += Math.pow(2, factor) * (post.getFrecuencia() * Math.log((float)cantDocumentos / (float)voc.getCant_documentos()));
                post.getDocumento().setIndiceRelevancia(ir);
                retFactores.put(post.getDocumento().getId(), factor);
                retDocumentos.put(post.getDocumento().getId(), post.getDocumento());
            }
        }
        ArrayList<Documento> ret = new ArrayList<>(retDocumentos.values());
        sortDoc.Sort(ret, 0, ret.size() - 1);
        return ret;
    }
}
