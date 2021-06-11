/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.accesodatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import utn.dlc.entidades.Post;
import utn.dlc.entidades.Vocabulario;
import utn.dlc.produces.DBManagerProduces;

/**
 *
 * @author CC31899077
 */
public class DBManagerPost extends DBManager {
    private static final String ID = "IdPost";
    private static final String IdVocabulario = "IdVocabulario";
    private static final String IdDocumento = "IdDocumento";
    private static final String Frecuencia = "Frecuencia";
    
    private final DBManagerVocabulario dbVocabulario;
    private final DBManagerDocumento dbDocumento;

    public DBManagerPost() {
        super();
        this.dbVocabulario = DBManagerProduces.createVocabulario();
        this.dbDocumento = DBManagerProduces.createDocumento();
    }
    
    public ArrayList<Post> searchByVocabulario(ArrayList<Vocabulario> vocabulario) throws Exception{
        ArrayList<Post> retPosteos = new ArrayList<>();
        StringBuilder sbQuery = new StringBuilder();
        
        sbQuery.append("SELECT p.ID As IdPost, p.IdVocabulario, p.IdDocumento, p.Frecuencia, ");
        sbQuery.append(" v.Palabra, v.CantDocumentos, v.MaxRf, ");
        sbQuery.append(" d.PathDoc ");
        sbQuery.append("FROM Post p ");
        sbQuery.append(" INNER JOIN Vocabulario v ON ");
        sbQuery.append(" v.ID = p.IdVocabulario ");
        sbQuery.append(" INNER JOIN Documentos d ON ");
        sbQuery.append(" d.ID = p.IdDocumento ");
        sbQuery.append("WHERE IdVocabulario IN (");
        for(Vocabulario palabra : vocabulario){
            if (vocabulario.indexOf(palabra) > 0){
                sbQuery.append(", ");
            }
            sbQuery.append(palabra.getId());
        }
        sbQuery.append(") ");
        try (ResultSet rs = this.executeQuery(sbQuery.toString())) {
            Post post;
            while ((post = buildPost(rs)) != null) {
                retPosteos.add(post);
            }
        }
        
        return retPosteos;
    }
    
    public long saveDB(ArrayList<Post> list) throws Exception {
        this.beginTransaction();
            
        this.insertDB(list);
        
        this.commit();
        return list.size();
    }

    private long saveDB(Post post, boolean batch) throws Exception {
        StringBuilder query = new StringBuilder();
        
        query.append("INSERT INTO Post");
        query.append("(IdVocabulario, IdDocumento, Frecuencia)");
        query.append("VALUES (?, ?, ?)");
        
        this.prepare(query.toString());
        this.setLong(1, post.getVocabulario().getId());
        this.setLong(2, post.getDocumento().getId());
        this.setLong(3, post.getFrecuencia());
        
        if (!batch){
            if (this.executeUpdate() == 0){
                throw new SQLException("Creating user failed, no ID obtained.");
            }
            else {
                return this.getIdAffected();
            }
        }
        else
        {
            this.addBatch();
            return 0;
        }
    }
    
    private void insertDB(ArrayList<Post> listToAdd) throws Exception{
        StringBuilder query = new StringBuilder();
        ArrayList<Long> listIndex = new ArrayList<>();
        
        query.append("INSERT INTO Post");
        query.append("(IdVocabulario, IdDocumento, Frecuencia)");
        query.append("VALUES (?, ?, ?)");
        
        int batchSize = 1000;
        int cant = 1;

        this.prepare(query.toString());
        for (Post post : listToAdd){
            this.setLong(1, post.getVocabulario().getId());
            this.setLong(2, post.getDocumento().getId());
            this.setLong(3, post.getFrecuencia());
            
            this.addBatch();
            if (cant % batchSize == 0){
                listIndex.addAll(this.executeBatchKeys());
            }
            cant++;
        }
        
        listIndex.addAll(this.executeBatchKeys());
        
        for (int i = 0; i < listToAdd.size(); i++) {
            Post post = listToAdd.get(i);
            post.setId(listIndex.get(i));
        }
    }

    public Post buildPost(ResultSet rs) throws SQLException {
        Post post = null;
        if (rs.next()) {
            post = new Post(rs.getLong(ID), 
                            dbVocabulario.build(rs),
                            dbDocumento.build(rs),
                            rs.getLong(Frecuencia));
        }
        return post;
    }

    public ArrayList<Post> searchByVocabulario(Vocabulario voc) throws Exception {
        ArrayList<Vocabulario> ret = new ArrayList<>();
        ret.add(voc);
        return this.searchByVocabulario(ret);
    }
    
}
