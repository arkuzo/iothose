/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.arkuzo.Sensors.data;

/**
 *
 * @author arseniy
 */
public class Resistance implements SensorData{
    
    double ohms;

    public Resistance() {
        ohms=0;
    }

    public Resistance(double ohms) {
        this.ohms = ohms;
    }

    public double getOhms() {
        return ohms;
    }

    public void update(double ohms) {
        this.ohms=ohms;
    }
    
}
