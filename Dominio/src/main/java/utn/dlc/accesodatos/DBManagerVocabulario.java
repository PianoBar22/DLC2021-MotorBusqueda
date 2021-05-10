/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.dlc.accesodatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
            vocabulario = new Vocabulario();
            vocabulario.setId(rs.getFloat(ID));
            vocabulario.setPalabra(rs.getString(PALABRA));
            vocabulario.setCantDocumentos(rs.getInt(CANT_DOC));
            vocabulario.setMaxRf(rs.getInt(MAXRF));
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
}
