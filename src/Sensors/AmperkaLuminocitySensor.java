/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sensors;

import DatabaseHandlers.EventWriter;
import Sensors.data.Luminocity;
import Sensors.data.Resistance;
import Sensors.data.SensorData;
import Sensors.data.Voltage;
import core.Data;
import core.Observer;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author arseniy
 */
public class AmperkaLuminocitySensor implements Sensor {
    int id;
    final Collection<Observer> listeners = new LinkedList<Observer>();
    final Luminocity luminocity = new Luminocity();
    String description;
    final Resistance resistance = new Resistance();
    final Voltage referenceVoltage;
    static final Resistance pullupResistance= new Resistance(9090);
    static final double multiplicationValue = 32017200;
    static final double powValue = 1.5832;

    public AmperkaLuminocitySensor(int id, String description, Voltage referenceVoltage) {
        this.id=id;
        this.description = description;
        this.referenceVoltage=referenceVoltage;
        EventWriter.write("Created Lightness sensor #"
                +id+" ("+this.description+")");
    }

    @Override
    public void notifyListeners() {
        listeners.forEach((x) -> x.handleEvent(luminocity));
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
            Voltage voltage = ((Voltage) data);
            resistance.update(pullupResistance.getOhms()/
                    (referenceVoltage.getVolts()/voltage.getVolts()-1));
            this.luminocity.updateLux(multiplicationValue/
                    Math.pow(resistance.getOhms(), powValue));
            EventWriter.sensorMessage(id,(float) this.luminocity.getLux(), "lux");
            this.notifyListeners();
        }
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Luminosity sensor "+luminocity.toString()+" ("+
                description+")";
    }

    @Override
    public SensorData read() {
        return luminocity;
    }

    @Override
    public void calibrate(Object calibrationMatrix) {
        System.out.println("Sensor calibrated");
    }
}
