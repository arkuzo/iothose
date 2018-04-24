/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servers;

import Fabrics.TransportFactory;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author arseniy
 */
public class TransportServerTest {
    TransportServer ts = TransportServer.getServer();
    
    public TransportServerTest() {
    }

    @Test
    public void testGetServer() {
        TransportServer ts2 = TransportServer.getServer();
        assertTrue(ts2.equals(ts));
    }

    @Test
    public void testStartListen() {
    }

    @Test
    public void testRun() {
    }
    
}
