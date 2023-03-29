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
public class Piatto implements Serializable{
    private final int id_piatto;
    private final String nome;
    private final String ingredienti;
    private final float costo;
    
    public Piatto(int id_piatto, String nome, String ingredienti, float costo) {
        this.id_piatto = id_piatto;
        this.nome = nome;
        this.ingredienti = ingredienti;
        this.costo = costo;
    }

    public int getId_piatto() {
        return id_piatto;
    }

    public String getNome() {
        return nome;
    }

    public String getIngredienti() {
        return ingredienti;
    }

    public float getCosto() {
        return costo;
    }
    
    @Override
    public String toString(){
        return "Piatto ["+id_piatto+"|"+nome+"]";
    }
}
