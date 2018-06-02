/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.arkuzo.Transport.Digital;

/**
 *
 * @author arseniy
 */
enum pinState {
    HIGH(1), 
    LOW(0);
    private final int numeric;
    private pinState(int numeric){
        this.numeric=numeric;
    }

    public int getNumeric() {
        return numeric;
    }
    
}
