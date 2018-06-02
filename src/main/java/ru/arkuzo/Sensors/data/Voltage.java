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
public class Voltage implements SensorData {
    private double voltage;
 
    public Voltage(double voltage) {
        this.voltage = voltage;
    }
    
    public double getVolts() {
        return this.voltage;
    }

    public void update(double volts) {
        this.voltage=volts;
    }

    @Override
    public String toString() {
        return "voltage " +voltage + " V"; //To change body of generated methods, choose Tools | Templates.
    }
    
}
