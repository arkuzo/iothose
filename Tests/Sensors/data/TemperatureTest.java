/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Sensors.data;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author arseniy
 */
public class TemperatureTest {
    Temperature temp;

    public TemperatureTest() {
    }
    
    @Before
    public void setup(){
        temp = new Temperature(0);
    }

    @Test
    public void testUpdateCelsium() {
        temp.updateCelsium(100);
        assertEquals(temp.getKelvin(), 373.15, 0.01);
        assertEquals(temp.getFahrenheit(), 211.971,0.1);
    }
    
    @Test
    public void testUpdateKelvin() {
        temp.updateKelvin(0);
        assertEquals(temp.getCelsium(), -273.15, 0.01);
        assertEquals(temp.getFahrenheit(), -459.67, 0.1);
    }
    
    @Test
    public void testUpdateFahrenheit() {
        temp.updateFahrenheit(98.6);
        assertEquals(temp.getCelsium(), 37, 0.01);
        assertEquals(temp.getKelvin(), 310.15, 0.1);
    }

}