/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Sensors.data;

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
    public void testGetScale() {
        assertEquals(lum.getScale(), 0, 0.001);
        lum=new Luminocity(4);
        assertEquals(lum.getScale(), 4, 0.001);
    }

    @Test
    public void testUpdate() {
        lum.update(2);
        assertEquals(lum.getScale(), 2, 0.001);
    }

}