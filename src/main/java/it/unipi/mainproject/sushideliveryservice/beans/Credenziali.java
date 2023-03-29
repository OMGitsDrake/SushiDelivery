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
public class Credenziali implements Serializable{
    public String user;
    public String pass;

    public Credenziali(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public String getNome() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
