/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitchblack.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *Luokka hallitsee tietokantayhteyttä.
 * @author JoonaHa
 * 
 * 
 * 
 * 
 */
public class Database {

    private String databaseAddress;

    /**
     * 
     * @param databaseAddress Käytettävän tietokannan osoite.
     * 
     */
    public Database(String databaseAddress)  {
        this.databaseAddress = databaseAddress;
    }

    /**
     * 
     * @return Muodostaa yhteyden tietokantaan ja palauttaa Connection-olion.
     * @throws SQLException 
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

}
