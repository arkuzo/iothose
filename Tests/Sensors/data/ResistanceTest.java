/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Sensors.data;

import ru.arkuzo.Sensors.data.Resistance;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author arseniy
 */
public class ResistanceTest {

    public ResistanceTest() {
    }

    @Test
    public void testUpdate() {
        Resistance res = new Resistance(10000);
        assertEquals(res.getOhms(), 10000, 0.01);
        res.update(15000);
        assertEquals(res.getOhms(), 15000, 0.01);
    }

}