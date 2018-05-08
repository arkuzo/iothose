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
import org.junit.Ignore;

/**
 *
 * @author arseniy
 */
@Ignore
public class TransportFactoryTest {
    TransportFactory testFactory;
    
    public TransportFactoryTest() throws SQLException {
        TransportFactory.init();
        this.testFactory = TransportFactory.getFactory();
    }
    
    @Ignore
    public void testGetFactory() throws SQLException {
        TransportFactory testFactory2 = TransportFactory.getFactory();
        assertTrue(testFactory2.equals(testFactory));
    }

    @Ignore
    public void testGetTransport() throws Exception {
        assertEquals(TransportFactory.getTransport(0).getID(), 0);
    }
    
}
