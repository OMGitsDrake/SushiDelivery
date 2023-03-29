/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.mainproject.sushideliveryservice.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lorenzo
 */
public class Carrello implements Serializable{
    private final List<Piatto> cart = new ArrayList<>();
    private final Utente u;
    private float totale;
    private int tempPoints;
    private final String tipo;
    
    public Carrello(Utente u, String tipo){
        this.u = u;
        this.tipo = tipo;
    }

    public List<Piatto> getCart() {
        return cart;
    }
    
    public Utente getUtente() {
        return u;
    }
    
    public void aggiungi(Piatto p){
        cart.add(p);
    }
    
    public void rimuovi(Piatto p){
        cart.remove(p);
    }

    public float getTotale() {
        return totale;
    }

    public void updateTotaleAdd(float add) {
        this.totale += add;
        this.totale = (float)(Math.round(this.totale*100))/100;
    }
    
    public void updateTotaleRemove(float add) {
        this.totale -= add;
        this.totale = (float)(Math.round(this.totale*100))/100;
    }

    public int getTempPoints() {
        return tempPoints;
    }
    
    public void updatePoints(int p){
        this.tempPoints = p;
    }

    public String getTipo() {
        return tipo;
    }
    
    @Override
    public String toString(){
        String res = "\nCarrello: ["+totale+" €] ["+tempPoints+" pts]\n";
        res += u.toString();
        for(Piatto p : cart){
            res += "\n"+p.toString();
        }
        
        return res;
    }
}
