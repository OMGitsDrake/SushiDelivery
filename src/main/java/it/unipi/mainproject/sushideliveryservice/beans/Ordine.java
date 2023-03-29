/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.mainproject.sushideliveryservice.beans;

import java.io.Serializable;

/**
 *
 * @author Lorenzo
 */
public class Ordine implements Serializable{
    private final String user;
    private final String orario;
    private final String indirizzo;
    private final String telefono;
    private final float totale;
    private final int puntiAcquisiti;

    public Ordine(String user, String orario, String indirizzo, String telefono, float totale, int pa) {
        this.user = user;
        this.orario = orario;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.totale = totale;
        this.puntiAcquisiti = pa;
    }

    public String getUser() {
        return user;
    }

    public String getOrario() {
        return orario;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getTelefono() {
        return telefono;
    }

    public float getTotale() {
        return totale;
    }

    public int getPuntiAcquisiti() {
        return puntiAcquisiti;
    }
}
