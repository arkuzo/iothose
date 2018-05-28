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
public class Temperature implements SensorData{
    
    private double celsium;

    public Temperature() {
        celsium=0;
    }

    public Temperature(double celsium) {
        this.celsium = celsium;
    }
    
    public double getCelsium() {
        return celsium;
    }

    public void updateCelsium(double scale) {
        this.celsium = scale;
    }
    
    public double getKelvin(){
        return celsium+273.15;
    }
    
    public void updateKelvin(double scale){
        celsium = scale-273.15;
    }

    public double getFahrenheit() {
        return celsium*1.8+32;
    }

    void updateFahrenheit(double scale) {
        celsium=(scale-32)*0.5555556;
    }
}
