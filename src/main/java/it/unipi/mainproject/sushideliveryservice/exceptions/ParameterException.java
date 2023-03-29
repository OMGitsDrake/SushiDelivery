/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unipi.mainproject.sushideliveryservice.exceptions;

/**
 *
 * @author Lorenzo
 */
public class ParameterException extends Exception{
    private final int code;

    public ParameterException(int code, String message) {
        super(message);
        this.code = code;
    }
    
    public int getCode(){
        return this.code;
    }
}
