/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.accesodatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utn.dlc.entidades.Documento;
import utn.dlc.entidades.Vocabulario;

/**
 *
 * @author CC31899077
 */
public class DBManagerDocumento extends DBManager{
    private static final String ID = "IdDocumento";
    private static final String PATH_DOC = "PathDoc";
    
    public Documento build(ResultSet rs){
        try {
            return new Documento(
                    rs.getLong(ID),
                    rs.getString(PATH_DOC));
        } catch (SQLException ex) {
            return null;
        }
    }
    
    private Documento buildDocumento(ResultSet rs) throws SQLException{
        Documento documento = null;
        if (rs.next()) {
            documento = this.build(rs);
        }
        return documento;
    }
    
    public long getCantDocumentos() throws Exception{
        StringBuilder query = new StringBuilder();
        
        query.append("SELECT COUNT(*) CantDocumentosTotal FROM Documentos");
        
        try (ResultSet rs = this.executeQuery(query.toString())) {
            if(rs.next()){
                return rs.getLong(1);
            }
            else{
                return 0;
            }
        }
    }
    
    public long saveDB(Documento documento) throws Exception {
        if (documento==null) throw new Exception("DBAlumno Error: Alumno NO especificado");
        long newId = 0;
        
        if(documento.getId() > 0){
            newId = this.updateDB(documento);
        }
        else
        {
            newId = this.insertDB(documento);
        }
        
        documento.setId(newId);
        return newId;
    }
    
    private long insertDB(Documento documento) throws Exception{
        StringBuilder query = new StringBuilder();
        
        query.append("INSERT INTO Documentos ");
        query.append("(PathDoc) ");
        query.append("VALUES (?)");
        
        this.prepare(query.toString());
        this.setString(1, documento.getPath());
        
        if (this.executeUpdate() == 0){
            throw new SQLException("Creating user failed, no ID obtained.");
        }
        else
        {
            return this.getIdAffected();
        }
    }
    
    private long updateDB(Documento documento) throws Exception{
        StringBuilder query = new StringBuilder();
        
        query.append("UPDATE Documentos SET ");
        query.append("PathDoc = ? ");
        query.append("WHERE ID = ?");
        
        this.prepare(query.toString());
        this.setString(1, documento.getPath());
        this.setLong(2, documento.getId());
        
        if (this.executeUpdate() == 0){
            throw new SQLException("Creating user failed, no ID obtained.");
        }
        else
        {
            return documento.getId(); //this.getIdAffected();
        }
    }
}
