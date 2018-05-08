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
    
    float lumens;

    public Luminocity() {
        lumens=0;
    }

    public Luminocity(float lumens) {
        this.lumens = lumens;
    }

    @Override
    public double getScale() {
        return lumens;
    }

    @Override
    public void update(double scale) {
        this.lumens =(float) scale;
    }
    
}
