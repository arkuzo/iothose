/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.arkuzo.Transport.Analog;

import ru.arkuzo.Transport.Analog.AnalogPin;
import ru.arkuzo.Sensors.data.Voltage;
import ru.arkuzo.Servers.SocketData;
import ru.arkuzo.Transport.FakeListener;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author arseniy
 */
public class AnalogPinTest {
    
    public AnalogPinTest() {
    }

    @Test
    public void testHandleEvent() throws InterruptedException {
        AnalogPin testPin = new AnalogPin(0, 0, 10, 5);
        FakeListener fl = new FakeListener();
        testPin.addListener(fl);
        Voltage voltage;
        assertEquals(testPin.getVoltage().getVolts(), 0, 0.01);
        testPin.handleEvent(new SocketData("ACH=0,819\r\n"));
        Thread.sleep(50);
        voltage = (Voltage) fl.getData();
        assertEquals(voltage.getVolts(), 4, 0.01);
        testPin.handleEvent(new SocketData("Fake input"));
        Thread.sleep(50);
        voltage = (Voltage) fl.getData();
        assertEquals(voltage.getVolts(), 4, 0.01);
        testPin.handleEvent(new SocketData("ACH=5,819\r\n"));
        Thread.sleep(50);
        voltage = (Voltage) fl.getData();
        assertEquals(voltage.getVolts(), 4, 0.01);
        testPin.handleEvent(new SocketData("ACH=0,8\r\n"));
        Thread.sleep(50);
        voltage = (Voltage) fl.getData();
        assertEquals(voltage.getVolts(), 0.04, 0.01);
        testPin.handleEvent(new SocketData("ACH=0,81\r\n"));
        Thread.sleep(50);
        voltage = (Voltage) fl.getData();
        assertEquals(voltage.getVolts(), 0.4, 0.01);
        testPin.handleEvent(new SocketData("ACH=0,819\r\n"));
        Thread.sleep(50);
        voltage = (Voltage) fl.getData();
        assertEquals(voltage.getVolts(), 4, 0.01);
        testPin.handleEvent(new SocketData("ACH=0,81900\r\n"));
        Thread.sleep(50);
        voltage = (Voltage) fl.getData();
        assertEquals(voltage.getVolts(), 4, 0.01);
       // fail("Test isn't finished");
    }
    
}
