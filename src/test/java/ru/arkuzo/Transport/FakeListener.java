/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.arkuzo.Transport;
import ru.arkuzo.core.Data;
import ru.arkuzo.core.Observer;

/**
 *
 * @author arseniy
 */
public class FakeListener implements Observer {
    Data data;

    @Override
    public void handleEvent(Data data) {
        this.data=data;
    }

    public Data getData() {
        return data;
    }
}
