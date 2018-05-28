/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.arkuzo.Servers;

import ru.arkuzo.Servers.SocketData;
import ru.arkuzo.core.Data;
import ru.arkuzo.core.Observer;

/**
 *
 * @author arseniy
 */
public class TestListenerForSocket implements Observer{
    private SocketData data= new SocketData(" ");

    @Override
    public void handleEvent(Data data) {
        if(data instanceof SocketData)
            this.data=(SocketData)data;
        //System.out.println("Handled "+this.data.getResponse());
    }
    
    public SocketData getData(){
        //System.out.println("Returning "+this.data.getResponse());
        return data;
    }
}
