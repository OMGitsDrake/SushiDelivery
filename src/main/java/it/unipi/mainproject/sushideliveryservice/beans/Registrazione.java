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
public class Registrazione implements Serializable{
    private final String user;
    private final String mail;
    private final String psw;
    private final String bdate;

    public Registrazione(String user, String mail, String psw, String bdate) {
        this.user = user;
        this.mail = mail;
        this.psw = psw;
        this.bdate = bdate;
    }

    public String getUser() {
        return user;
    }

    public String getMail() {
        return mail;
    }

    public String getPsw() {
        return psw;
    }

    public String getBdate() {
        return bdate;
    }
}
