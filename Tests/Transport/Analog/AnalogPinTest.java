/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transport.Analog;

import Sensors.data.Voltage;
import Servers.SocketData;
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
    public void testHandleEvent() {
        AnalogPin testPin = new AnalogPin(0, 0, 10, 5);
        assertEquals(testPin.getVoltage().getScale(), 0,0.01);
        testPin.handleEvent(new SocketData("ACH=5,819\r\n"));
        assertEquals(testPin.getVoltage().getScale(), 0,0.01);
        testPin.handleEvent(new SocketData("Fake input"));
        assertEquals(testPin.getVoltage().getScale(), 0,0.01);
        testPin.handleEvent(new SocketData("ACH=0,819\r\n"));
        assertEquals(testPin.getVoltage().getScale(), 4,0.01);
    }
    
}
