/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.accesodatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import utn.dlc.entidades.Vocabulario;

/**
 *
 * @author CC31899077
 */
public class DBManagerVocabulario extends DBManager{
    private static final String ID = "ID";
    private static final String PALABRA = "Palabra";
    private static final String CANT_DOC = "CantDocumentos";
    private static final String MAXRF = "MaxRf";
    
    private static Vocabulario buildVocabulario(ResultSet rs) throws SQLException {
        Vocabulario vocabulario = null;
        if (rs.next()) {
            vocabulario = new Vocabulario(
                            rs.getLong(ID), 
                            rs.getString(PALABRA),
                            rs.getLong(CANT_DOC),
                            rs.getLong(MAXRF));
        }
        return vocabulario;
    }

    public DBManagerVocabulario() {
        super();
    }
    
    
    /**
     * Genera una lista de alumnos.
     *
     * @param db
     * @param limit
     * @param offset
     * @return
     * @throws Exception
     */
    public List loadList(int limit, int offset) throws Exception {
        if (limit<0) throw new Exception("DBVocabulario Error: limit incorrecto");
        if (offset<0) throw new Exception("DBVocabulario Error: offset incorrecto");
        List vocabularios = new ArrayList();
        String query = "SELECT * " +
                "FROM Vocabulario v " +
              //  "WHERE " +
                "ORDER BY v.CantDocumentos desc ";
        if (limit>0) query += "LIMIT " + limit + "OFFSET " + offset;
        ResultSet rs = this.executeQuery(query);

        Vocabulario vocabulario = null;
        while ((vocabulario = buildVocabulario(rs)) != null) {
            vocabularios.add(vocabulario);
        }
        rs.close();

        return vocabularios;
    }

    /**
     * Genera una lista de alumnos.
     *
     * @param db
     * @return
     * @throws Exception
     */
    public List loadList() throws Exception {
        return loadList(0, 0);
    }
    
    public HashMap<String, Vocabulario> loadListHash() throws Exception{
        ArrayList<Vocabulario> list = (ArrayList<Vocabulario>) this.loadList(0, 0);
        HashMap<String, Vocabulario> map = new HashMap<String, Vocabulario>();
        
        for(Vocabulario voc : list){
            map.put(voc.getPalabra(), voc);
        }
        return map;
    }
    /**
     * Guarda un alumno en la ddbb.
     *
     * @param db
     * @param vocabulario
     * @return
     * @throws Exception
     */
    public long saveDB(Vocabulario vocabulario, boolean batch) throws Exception {
        if (vocabulario==null) throw new Exception("DBAlumno Error: Alumno NO especificado");
        
        if(vocabulario.getId() > 0){
            return this.updateDB(vocabulario, batch);
        }
        else
        {
            return this.insertDB(vocabulario, batch);
        }
    }
    
    private long updateDB(Vocabulario vocabulario, boolean batch) throws Exception{
        StringBuilder query = new StringBuilder();
        
        query.append("UPDATE Vocabulario SET ");
        query.append("CantDocumentos = ?,");
        query.append("MaxRf = ? ");
        query.append("WHERE ID = ?");
        
        this.prepare(query.toString());
        this.setLong(1, vocabulario.getCant_documentos());
        this.setLong(2, vocabulario.getMax_rf());
        this.setLong(3, vocabulario.getId());
        
        if (!batch){
            if (this.executeUpdate() == 0){
                throw new SQLException("Creating user failed, no ID obtained.");
            }
            else
            {
                return vocabulario.getId(); //this.getIdAffected();
            }
        }else{
            this.addBatch();
            return 0;
        }
    }
    
    private long insertDB(Vocabulario vocabulario, boolean batch) throws Exception{
        StringBuilder query = new StringBuilder();
        
        query.append("INSERT INTO Vocabulario ");
        query.append("(Palabra, CantDocumentos, MaxRf) ");
        query.append("VALUES(?, ?, ?)");
        
        this.prepare(query.toString());
        this.setString(1, vocabulario.getPalabra());
        this.setLong(2, vocabulario.getCant_documentos());
        this.setLong(3, vocabulario.getMax_rf());
        
        if (!batch){
            if (this.executeUpdate() == 0){
                throw new SQLException("Creating user failed, no ID obtained.");
            }
            else
            {
                return vocabulario.getId(); //this.getIdAffected();
            }
        }else{
            this.addBatch();
            return 0;
        }
    }
    
    public float saveDB(ArrayList<Vocabulario> list) throws Exception {
        if (list==null) throw new Exception("DBAlumno Error: Alumno NO especificado");
        this.beginTransaction();
        
        ArrayList<Vocabulario> listToAdd = list.stream().filter((Vocabulario voc) -> voc.getId() <= 0)
                                                        .collect(Collectors.toCollection(ArrayList::new));
        
        ArrayList<Vocabulario> listToUpdate = list.stream().filter((Vocabulario voc) -> voc.getId() > 0)
                                                        .collect(Collectors.toCollection(ArrayList::new));
        
        if(!listToAdd.isEmpty())
            this.insertDB(listToAdd);
        
        if(!listToUpdate.isEmpty())
            this.updateDB(listToUpdate);
        
        this.commit();
        return listToAdd.size() + listToUpdate.size();
    }

    private void insertDB(ArrayList<Vocabulario> listToAdd) throws Exception {
        StringBuilder query = new StringBuilder();
        ArrayList<Long> listIndex = new ArrayList<>();
        
        query.append("INSERT INTO Vocabulario ");
        query.append("(Palabra, CantDocumentos, MaxRf) ");
        query.append("VALUES(?, ?, ?)");
        
        this.prepare(query.toString());
        
        int batchSize = 1000;
        int cant = 1;
        
        for(Vocabulario vocabulario : listToAdd){
            this.setString(1, vocabulario.getPalabra());
            this.setLong(2, vocabulario.getCant_documentos());
            this.setLong(3, vocabulario.getMax_rf());
            this.addBatch();
            
            if (cant % batchSize == 0){
                listIndex.addAll(this.executeBatchKeys());
            }
            cant++;
        }
        
        listIndex.addAll(this.executeBatchKeys());
        
        for (int i = 0; i < listToAdd.size(); i++) {
            Vocabulario voc = listToAdd.get(i);
            voc.setId(listIndex.get(i));
        }
    }

    private void updateDB(ArrayList<Vocabulario> listToUpdate) throws Exception {
        StringBuilder query = new StringBuilder();
        
        query.append("UPDATE Vocabulario SET ");
        query.append("CantDocumentos = ?,");
        query.append("MaxRf = ? ");
        query.append("WHERE ID = ?");
        
        this.prepare(query.toString());       
        
        int batchSize = 1000;
        int cant = 1;
        
        for(Vocabulario vocabulario : listToUpdate){
            this.setLong(1, vocabulario.getCant_documentos());
            this.setLong(2, vocabulario.getMax_rf());
            this.setLong(3, vocabulario.getId());
            this.addBatch();
            
            if (cant % batchSize == 0){
                this.executeBatch();
            }
            cant++;
        }
        
        this.executeBatchKeys();
    }
}
