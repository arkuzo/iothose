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
public class Luminocity implements SensorData {
    
    double lux;

    public Luminocity() {
        lux=0;
    }

    public Luminocity(float lux) {
        this.lux = lux;
    }

    public double getLux() {
        return lux;
    }

    public void updateLux(double lux) {
        this.lux = lux;
    }
    
}
