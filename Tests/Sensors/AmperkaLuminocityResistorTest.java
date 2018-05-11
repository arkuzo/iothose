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
public class AmperkaLuminocityResistorTest {
    AmperkaLuminocitySensor alr = new AmperkaLuminocitySensor(0,"test");

    public AmperkaLuminocityResistorTest() {
    }

    @Test
    public void testTransformation() throws InterruptedException {
        FakeListener fl = new FakeListener();
        alr.addListener(fl);
        alr.handleEvent(new Voltage((float) 3.4));
        assertEquals(alr.data.getScale(), 1.6,0.01);
        Thread.sleep(20);
        assertTrue(fl.getData() instanceof Luminocity);
        assertEquals(((Luminocity)fl.getData()).getScale(), 1.6, 0.01);
    }

}