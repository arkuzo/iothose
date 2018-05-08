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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arseniy
 */
public class Action implements Observer, Observable{
    LinkedList<Observer> listeners = new LinkedList<>();
    //Behavior beh = new Behavior();
    Data inputData;

    @Override
    public void handleEvent(Data data) {
        this.inputData=data;
        this.update();
    }

    @Override
    public void update() {
        try {
            throw new Exception ("Does not realized");
            // listeners.stream().forEach(x -> {x.handleEvent(beh.compute());});
        } catch (Exception ex) {
            Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
        }
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
