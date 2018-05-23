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
public class Temperature implements SensorData{
    
    private double temperature;

    public Temperature() {
        temperature=0;
    }

    public Temperature(double temperature) {
        this.temperature = temperature;
    }
    
    @Override
    public double getScale() {
        return temperature;
    }

    @Override
    public void update(double scale) {
        this.temperature = scale;
    }
    
}
