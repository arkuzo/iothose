/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sensors;

import Sensors.data.Luminocity;
import Sensors.data.Voltage;
import core.Data;
import core.Observable;
import core.Observer;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author arseniy
 */
public class AmperkaLuminocityResistor implements Observable, Observer {
    final Collection<Observer> listeners = new LinkedList<Observer>();
    final Luminocity data = new Luminocity();

    @Override
    public void update() {
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
            this.update();
        }
    }
    
}
