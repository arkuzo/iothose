/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factories;

import Factories.TransportFactory;
import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author arseniy
 */
public class TransportFactoryTest {
    TransportFactory testFactory;
    
    public TransportFactoryTest() throws SQLException {
        this.testFactory = TransportFactory.getFactory();
    }
    
    @Test
    public void testGetFactory() throws SQLException {
        TransportFactory testFactory2 = TransportFactory.getFactory();
        assertTrue(testFactory2.equals(testFactory));
    }

    @Test
    public void testGetTransport() throws Exception {
        assertEquals(TransportFactory.getTransport(0).getID(), 0);
    }
    
}
