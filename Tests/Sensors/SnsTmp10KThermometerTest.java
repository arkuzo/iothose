/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Sensors;

import Sensors.data.*;
import Transport.FakeListener;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author arseniy
 */
public class SnsTmp10KThermometerTest {

    public SnsTmp10KThermometerTest() {
    }

    @Test
    public void testSomeMethod() {
        SnsTmp10KThermometer therm = new SnsTmp10KThermometer(
                new Voltage(5.0), new Resistance(9090.9),0);
        FakeListener fl = new FakeListener();
        therm.addListener(fl);
        therm.handleEvent(new Voltage(2.283));
        assertEquals(therm.resistance.getScale(), 10821, 100);
        assertEquals(therm.data.getScale(), 22.9, 0.1);
        Temperature temp = (Temperature)fl.getData();
        assertEquals(temp.getScale(),22.9,0.1);
    }

}