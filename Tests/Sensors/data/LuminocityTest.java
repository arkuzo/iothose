/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Sensors.data;

import ru.arkuzo.Sensors.data.Luminocity;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author arseniy
 */
public class LuminocityTest {
    Luminocity lum = new Luminocity();
    

    public LuminocityTest() {
    }

    @Test
    public void testGetLux() {
        assertEquals(lum.getLux(), 0, 0.001);
        lum=new Luminocity(4);
        assertEquals(lum.getLux(), 4, 0.001);
    }

    @Test
    public void testUpdate() {
        lum.updateLux(2);
        assertEquals(lum.getLux(), 2, 0.001);
    }

}