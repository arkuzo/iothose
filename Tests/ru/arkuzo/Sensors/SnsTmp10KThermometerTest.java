/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.arkuzo.Sensors;

import ru.arkuzo.Sensors.data.Temperature;
import ru.arkuzo.Sensors.data.Resistance;
import ru.arkuzo.Sensors.SnsTmp10KThermometer;
import ru.arkuzo.Sensors.data.Voltage;
import ru.arkuzo.Transport.FakeListener;
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
    public void testSomeMethod() throws InterruptedException {
        SnsTmp10KThermometer therm = new SnsTmp10KThermometer(
                new Voltage(5.0), new Resistance(9090.9),0);
        FakeListener fl = new FakeListener();
        therm.addListener(fl);
        therm.handleEvent(new Voltage(2.283));
        assertEquals(therm.resistance.getOhms(), 10821, 100);
        assertEquals(therm.temperature.getCelsium(), 22.9, 0.1);
        therm.handleEvent(new Voltage(1.2));
        assertEquals(therm.resistance.getOhms(), 28700, 100);
        assertEquals(therm.temperature.getCelsium(), 0, 0.1);
        therm.handleEvent(new Voltage(4.5));
        assertEquals(therm.resistance.getOhms(), 990, 100);
        assertEquals(therm.temperature.getCelsium(), 100, 1);
        Thread.sleep(20);
        Temperature temp = (Temperature)fl.getData();
        assertEquals(temp.getCelsium(),100, 1);
    }

}