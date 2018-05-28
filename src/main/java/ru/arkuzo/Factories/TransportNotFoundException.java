/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.arkuzo.Factories;

/**
 *
 * @author arseniy
 */
public class TransportNotFoundException extends Exception {

    public TransportNotFoundException() {
    }
    
    public TransportNotFoundException(String errMsg) {
        super(errMsg);
    }
    
}
