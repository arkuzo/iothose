/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.arkuzo.Transport.Digital;

import ru.arkuzo.core.Data;

/**
 *
 * @author arseniy
 */
public class DigitalPinData implements Data{
    
    private pinState state;

    public DigitalPinData(pinState state) {
        this.state = state;
    }

    pinState getState() {
        return state;
    }
    
    public void update(pinState state){
        this.state=state;
    }
    
}
