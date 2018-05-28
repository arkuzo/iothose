/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Sensors;

import Sensors.data.Luminocity;
import Sensors.data.Voltage;
import Transport.FakeListener;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author arseniy
 */
public class AmperkaLuminocitySensorTest {
    AmperkaLuminocitySensor alr = new AmperkaLuminocitySensor(0,"test",new Voltage(5));

    public AmperkaLuminocitySensorTest() {
    }

    @Test
    public void testTransformation() throws InterruptedException {
        FakeListener fl = new FakeListener();
        alr.addListener(fl);
        alr.handleEvent(new Voltage(1));
        assertEquals(alr.luminocity.getLux(), 155,1);
        alr.handleEvent(new Voltage(0.5));
        assertEquals(alr.luminocity.getLux(), 560,10);
        alr.handleEvent(new Voltage(4));
        assertEquals(alr.luminocity.getLux(), 1.92,0.1);
        alr.handleEvent(new Voltage(0.1));
        assertEquals(alr.luminocity.getLux(), 8200,100);
        Thread.sleep(20);
        assertTrue(fl.getData() instanceof Luminocity);
        assertEquals(((Luminocity)fl.getData()).getLux(), 8200,100);
    }

}