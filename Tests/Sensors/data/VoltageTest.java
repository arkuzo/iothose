/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sensors.data;

/**
 *
 * @author arseniy
 */
import static junit.framework.TestCase.*;
import org.junit.*;
public class VoltageTest {

    @Test
    public void testGetScale() {
        Voltage testVoltage = new Voltage (0);
        assertEquals(testVoltage.getScale(),.0);
        testVoltage.update(2);
        assertEquals(testVoltage.getScale(),2,0.001);
    }
}
