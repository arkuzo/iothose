/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.arkuzo.Transport;

import ru.arkuzo.Servers.SocketData;
import ru.arkuzo.Transport.Arduino;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author arseniy
 */
public class ArduinoTest {
    FakeListener fl = new FakeListener();
    Arduino ard = new Arduino(0, "test");
    
    public ArduinoTest() {
    }

    @Test
    public void testGetID() throws UnknownHostException {
        assertEquals(ard.getID(), 0);
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
