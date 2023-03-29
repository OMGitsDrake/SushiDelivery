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
public class Orario implements Serializable{
    private final String fascia_oraria;

    public Orario(String fascia_oraria) {
        this.fascia_oraria = fascia_oraria;
    }

    public String getFascia_oraria() {
        return fascia_oraria;
    }
}
