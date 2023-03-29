/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.mainproject.sushideliveryservice.beans;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Lorenzo
 */
public class HttpConnector {

    private HttpConnector() {}
    
    /**
     * Restituisce la HttpURLConnection all'url indicato come parametro, preparando una richiesta POST
     * @param urlLocation target url
     * @return HttpURLConnection all'url
     * @throws IOException 
     */
    public static HttpURLConnection connectPOST(String urlLocation) throws IOException{
        URL url = new URL(urlLocation);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        
        return con;
    }
    
    /**
     * Restituisce la HttpURLConnection all'url indicato come parametro, preparando una richiesta DELETE
     * @param urlLocation target url con parametri della querysting
     * @return HttpURLConnection all'url
     * @throws IOException 
     */
    public static HttpURLConnection connectDELETE(String urlLocation) throws IOException{
        URL url = new URL(urlLocation);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("DELETE");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        
        return con;
    }
}
