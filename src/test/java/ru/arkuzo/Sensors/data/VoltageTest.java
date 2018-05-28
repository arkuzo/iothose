/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.arkuzo.Sensors.data;

/**
 *
 * @author arseniy
 */
import ru.arkuzo.Sensors.data.Voltage;
import static junit.framework.TestCase.*;
import org.junit.*;
public class VoltageTest {

    @Test
    public void testGetScale() {
        Voltage testVoltage = new Voltage (0);
        assertEquals(testVoltage.getVolts(),.0,0.01);
        testVoltage.update(2);
        assertEquals(testVoltage.getVolts(),2,0.001);
        testVoltage = new Voltage(15);
        assertEquals(testVoltage.getVolts(),15,0.001);
    }
}
