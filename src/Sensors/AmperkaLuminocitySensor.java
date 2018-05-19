/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sensors;

import DatabaseHandlers.EventWriter;
import Sensors.data.Luminocity;
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
    final Luminocity data = new Luminocity();
    String description;

    public AmperkaLuminocitySensor(int id, String description) {
        this.id=id;
        this.description = description;
        EventWriter.write("Created Lightness sensor #"
                +id+" ("+this.description+")");
    }

    @Override
    public void notifyListeners() {
        listeners.forEach((x) -> x.handleEvent(data));
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
            this.data.update(5-((Voltage) data).getScale());
            EventWriter.sensorMessage(id,(float) this.data.getScale(), "lumen");
            this.notifyListeners();
        }
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Luminosity sensor "+data.toString()+" ("+
                description+")";
    }

    @Override
    public SensorData read() {
        return data;
    }

    @Override
    public void calibrate(Object calibrationMatrix) {
        System.out.println("Sensor calibrated");
    }
}
