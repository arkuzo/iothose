/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.arkuzo.Sensors;

import ru.arkuzo.DatabaseHandlers.EventWriter;
import ru.arkuzo.core.Data;
import ru.arkuzo.core.Observer;
import java.util.Collection;
import java.util.LinkedList;
import ru.arkuzo.Sensors.data.Resistance;
import ru.arkuzo.Sensors.data.SensorData;
import ru.arkuzo.Sensors.data.Temperature;
import ru.arkuzo.Sensors.data.Voltage;

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
            resistance.update(referenceVoltage.getVolts()
                            * pulldownResistor.getOhms()
                            / voltage.getVolts()
                                - pulldownResistor.getOhms());
            this.temperature.updateCelsium(b/Math.log(resistance.getOhms()/0.09919)
                -273.15);
            EventWriter.sensorMessage(id, this.temperature.getCelsium(), "celsium");
            this.notifyListeners();
        }
    }
    
}
