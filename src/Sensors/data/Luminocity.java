/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sensors.data;

/**
 *
 * @author arseniy
 */
public class Luminocity implements SensorData {
    
    float lux;

    public Luminocity() {
        lux=0;
    }

    public Luminocity(float lumens) {
        this.lux = lumens;
    }

    @Override
    public double getScale() {
        return lux;
    }

    @Override
    public void update(double scale) {
        this.lux =(float) scale;
    }
    
}
