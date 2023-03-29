/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package it.unipi.mainproject.sushideliveryservice;

import com.google.gson.JsonObject;
import it.unipi.mainproject.sushideliveryservice.beans.HttpConnector;
import it.unipi.mainproject.sushideliveryservice.beans.JSONReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Lorenzo
 */
public class ServerConnectionTest {
    protected static final Logger logger = LogManager.getLogger(ServerConnectionTest.class);
    
    public ServerConnectionTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testHttpURLConnectionPOST() {
        try{
            HttpURLConnection con = HttpConnector.connectPOST("http://localhost:8080/test/server_con_post");
            
            String jsonTest = "{\"test\": \"connessione\"}";

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonTest.getBytes("utf-8");
                os.write(input, 0, input.length);			
            }

            JsonObject response = JSONReader.read(con);
            
            if(response.get("ok").getAsBoolean()){
                logger.info("OK");
                System.out.println("+-----------------------------------------+");
                System.out.println("| Test - Connessione al Server (POST): OK |");
                System.out.println("+-----------------------------------------+");
            } else {
                logger.info("ERR");
                System.out.println("+-----------------------------------+");
                fail("| Errore di connettivita` al Server |");
                System.out.println("+-----------------------------------+");
            }
        } catch(IOException e){
            logger.error(e.getMessage());
            fail("Errore - Connessione al Server (POST): " + e.getMessage());
        }
    }
    
    @Test
    public void testHttpURLConnectionDELETE() {
        try{
            HttpURLConnection con = HttpConnector.connectDELETE("http://localhost:8080/test/server_con_delete");
            
            String jsonTest = "{\"test\": \"connessione\"}";

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonTest.getBytes("utf-8");
                os.write(input, 0, input.length);			
            }

            JsonObject response = JSONReader.read(con);
            
            if(response.get("ok").getAsBoolean()){
                logger.info("OK");
                System.out.println("+-------------------------------------------+");
                System.out.println("| Test - Connessione al Server (DELETE): OK |");
                System.out.println("+-------------------------------------------+");
            } else {
                logger.info("ERR");
                System.out.println("+-----------------------------------+");
                fail("| Errore di connettivita` al Server |");
                System.out.println("+-----------------------------------+");
            }
        } catch(IOException e){
            logger.error(e.getMessage());
            fail("Errore - Connessione al Server (DELETE): " + e.getMessage());
        }
    }
}