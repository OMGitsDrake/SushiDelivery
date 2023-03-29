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
public class Preferito implements Serializable{
    private final int id_piatto;
    private final String username;

    public Preferito(int id_piatto, String username) {
        this.id_piatto = id_piatto;
        this.username = username;
    }

    public int getId_piatto() {
        return id_piatto;
    }

    public String getUsername() {
        return username;
    }
}
