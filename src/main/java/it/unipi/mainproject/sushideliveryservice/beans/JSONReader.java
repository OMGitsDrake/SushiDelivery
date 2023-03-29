/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.mainproject.sushideliveryservice.beans;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 *
 * @author Lorenzo
 */
public class JSONReader {

    private static final Gson gson = new Gson();
    
    private JSONReader() {}
    
    /**
     * Restituisce la risposta in formato Json a una richista mandata al server
     * 
     * @param connection HttpUrlConnection da cui richiedere l'inputstream.
     * @return JsonObject la risposta mandata dal server in formato JSONResponse (Java bean in it.unipi.mainproject.SushiDeliverySevrer.beans)
     * @throws IOException 
     */
    public static JsonObject read(HttpURLConnection connection) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

        String inputLine;
        StringBuilder content = new StringBuilder();

        while((inputLine = in.readLine()) != null){
            content.append(inputLine);
        }

        JsonElement json = gson.fromJson(content.toString(), JsonElement.class);
        JsonObject response = json.getAsJsonObject();
        
        return response;
    }
}
