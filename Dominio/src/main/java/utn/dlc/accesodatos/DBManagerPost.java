/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.accesodatos;

import java.sql.SQLException;
import java.util.ArrayList;
import utn.dlc.entidades.Post;
import utn.dlc.produces.DBManagerProduces;

/**
 *
 * @author CC31899077
 */
public class DBManagerPost extends DBManager {
    private final DBManagerVocabulario dbVocabulario;
    private final DBManagerDocumento dbDocumento;

    public DBManagerPost() {
        super();
        this.dbVocabulario = DBManagerProduces.createVocabulario();
        this.dbDocumento = DBManagerProduces.createDocumento();
    }
    
    public long saveDB(ArrayList<Post> list) throws Exception {
        for(Post post : list){
            this.saveDB(post);
        }
        return 0;
    }

    private long saveDB(Post post) throws Exception {
        StringBuilder query = new StringBuilder();
        long idVoc = this.dbVocabulario.saveDB(post.getVocabulario());
        long idDoc = this.dbDocumento.saveDB(post.getDocumento());
        
        query.append("INSERT INTO Post");
        query.append("(IdVocabulario, IdDocumento, Frecuencia)");
        query.append("VALUES (?, ?, ?)");
        
        this.prepare(query.toString());
        this.setLong(1, idVoc);
        this.setLong(2, idDoc);
        this.setLong(3, post.getFrecuencia());
        
        if (this.executeUpdate() == 0){
            throw new SQLException("Creating user failed, no ID obtained.");
        }
        else
        {
            return this.getIdAffected();
        }
    }
    
}
