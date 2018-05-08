/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transport;

import Servers.*;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author arseniy
 */
public class ArduinoTest {
    FakeInputStream fis = new FakeInputStream();
    FakeSocket fs = new FakeSocket(fis);
    FakeListener fl = new FakeListener();
    FakeOutputStream fos;
    Arduino ard;
    
    public ArduinoTest() {
    }
    
    @Before
    public void setUp () throws UnknownHostException{
        fos = fs.getFos();
        ard = new Arduino(0, fs, "test");
    }

    @Test
    public void testSetSocket() throws UnknownHostException {
        FakeSocket fs2 = new FakeSocket(fis);
        SocketListener sl = ard.getSocketListener();
        assertEquals(ard.getSocketListener(), sl);
        ard.setSocket(fs2);
        assertNotEquals(ard.getSocketListener(), sl);
        
    }

    @Test
    public void testGetID() throws UnknownHostException {
        assertEquals(ard.getID(), 0);
    }

    @Test
    public void testSend() throws UnknownHostException {
        ard.send("asdf");
        assertEquals("asdf", fos.getOutput());
    }

    @Test
    public void testIsActive() throws UnsupportedEncodingException, InterruptedException {
        ard.send("AT\r\n");
        assertEquals("AT\r\n", fos.getOutput());
        fis.setInput("OK\r\n");
        assertTrue(ard.isActive());
    }

    @Test
    public void testTransportFunctions() throws UnsupportedEncodingException, InterruptedException {
        ard.addListener(fl);
        ard.handleEvent(new SocketData("OK\r\n"));
        Thread.sleep(20);
        SocketData tData = (SocketData)fl.getData();
        assertEquals(tData.getResponse(),"OK\r\n");
    }
    
}
