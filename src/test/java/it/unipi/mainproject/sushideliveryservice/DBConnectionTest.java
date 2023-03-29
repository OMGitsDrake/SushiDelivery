/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package it.unipi.mainproject.sushideliveryservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Lorenzo
 */
public class DBConnectionTest {
    private static final Logger logger = LogManager.getLogger(DBConnectionTest.class);
    
    public DBConnectionTest() {
    }

    /**
     * Test della connessione al DB per l'inizializzazione dei dati lato client
     */
    @Test
    public void testDBConnection() {
        try{
            Connection c = DriverManager.getConnection(App.connectionURL, App.user, App.pass);
            
            if(c.isValid(0)){
                logger.info("OK");
                System.out.println("+------------------------------+");
                System.out.println("| Test - Connessione al DB: OK |");
                System.out.println("+------------------------------+");
            } else {
                logger.info("ERR");
                System.out.println("+-------------------------------------------------------------+");
                fail("| Connessione non valida al momento del test di connettivita` |");
                System.out.println("+-------------------------------------------------------------+");
            }
        } catch(SQLException e){
            logger.error(" - " + e.getMessage() + " [" + e.getSQLState() + ']');
            fail("Errore - Connessione al DB: " + e.getMessage());
        }
    }
    
}
