/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.arkuzo.Transport.Digital;

import ru.arkuzo.Transport.Digital.DigitalPin;
import ru.arkuzo.Transport.Digital.pinState;
import ru.arkuzo.Servers.SocketData;
import ru.arkuzo.Transport.pinMode;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author arseniy
 */
public class DigitalPinTest {
    
    public DigitalPinTest() {
    }

    @Test
    public void testHandleEvent() {
        DigitalPin testPin = new DigitalPin(0,5,pinMode.OUTPUT);
        testPin.handleEvent(new SocketData("DCH=5,1\r\n"));
        assertEquals(testPin.getData().getState(), pinState.HIGH);
        testPin.handleEvent(new SocketData("DCH=5,0\r\n"));
        assertEquals(testPin.getData().getState(), pinState.LOW);
        testPin.handleEvent(new SocketData("DCH=4,1\r\n"));
        assertEquals(testPin.getData().getState(), pinState.LOW);
        testPin.handleEvent(new SocketData("DCH=5,2\r\n"));
        assertEquals(testPin.getData().getState(), pinState.LOW);
    }
    
    @Test
    public void testGetId(){
        DigitalPin testPin = new DigitalPin(0,5,pinMode.OUTPUT);
        assertEquals(testPin.getId(), 0);
    }
    
}
