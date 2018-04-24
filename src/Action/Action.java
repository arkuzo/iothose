/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import core.Data;
import core.Observable;
import core.Observer;
import java.util.LinkedList;

/**
 *
 * @author arseniy
 */
public class Action implements Observer, Observable{
    LinkedList<Observer> listeners = new LinkedList<>();
    Behavior beh = new Behavior();
    Data inputData;

    @Override
    public void handleEvent(Data data) {
        this.inputData=data;
        this.update();
    }

    @Override
    public void update() {
        listeners.stream().forEach(x -> {x.handleEvent(beh.compute());});
    }

    @Override
    public void addListener(Observer listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Observer listener) {
        listeners.remove(listener);
    }
    
}
