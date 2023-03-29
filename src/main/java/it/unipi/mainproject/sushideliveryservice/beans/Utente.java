/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.mainproject.sushideliveryservice.beans;

/**
 *
 * @author Lorenzo
 */
public class Utente {
    private final String username;
    private final String password;
    private final String email;
    private int punti;

    public Utente(String username, String password, String email, int punti) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.punti = punti;
    }

    public int getPunti() {
        return punti;
    }

    public void setPunti(int punti) {
        this.punti = punti;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
    
    @Override
    public String toString(){
        String str = "Username: " + username + " mail: " + email + " punti: " + punti;
        return str;
    }
}
