/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sensors;

import DatabaseHandlers.EventWriter;
import Sensors.data.*;
import core.Data;
import core.Observer;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author arseniy
 */
public class SnsTmp10KThermometer implements Sensor{
    int id;
    final Collection<Observer> listeners = new LinkedList<Observer>();
    final Temperature temperature = new Temperature();
    final Resistance resistance = new Resistance();
    String description;
    final Voltage referenceVoltage;
    final Resistance pulldownResistor;
    final static double b=3435;

    public SnsTmp10KThermometer(Voltage referenceVoltage, 
            Resistance pulldownResistor,
                int id) {
        this.referenceVoltage = referenceVoltage;
        this.pulldownResistor = pulldownResistor;
        this.id=id;
    }

    @Override
    public SensorData read() {
        return temperature;
    }

    @Override
    public void calibrate(Object calibrationMatrix) {
        System.out.println("Sensor calibrated");
    }

    @Override
    public void notifyListeners() {
        listeners.forEach((x) -> x.handleEvent(temperature));
    }

    @Override
    public void addListener(Observer listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Observer listener) {
        listeners.remove(listener);
    }

    @Override
    public void handleEvent(Data data) {
        if(data instanceof Voltage){
            Voltage voltage = (Voltage) data;
            resistance.update(referenceVoltage.getScale()
                            * pulldownResistor.getScale()
                            / voltage.getScale()
                                - pulldownResistor.getScale());
            this.temperature.update(b/Math.log(resistance.getScale()/0.09919)
                -273.15);
            EventWriter.sensorMessage(id, this.temperature.getScale(), "celsium");
            this.notifyListeners();
        }
    }
    
}
