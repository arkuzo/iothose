/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fabrics;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author arseniy
 */
public class TransportFactoryTest {
    TransportFactory testFactory = TransportFactory.getFactory();
    
    public TransportFactoryTest() {
    }
    
    @Test
    public void testGetFactory() {
        TransportFactory testFactory2 = TransportFactory.getFactory();
        assertTrue(testFactory2.equals(testFactory));
    }

    @Test
    public void testGetTransport() throws Exception {
        assertEquals(TransportFactory.getTransport(0).getID(), 0);
    }
    
}
