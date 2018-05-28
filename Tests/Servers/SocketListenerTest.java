/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servers;

import ru.arkuzo.Servers.SocketListener;
import ru.arkuzo.Transport.Transport;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author arseniy
 */
public class SocketListenerTest {
    TestListenerForSocket tl = new TestListenerForSocket();
    FakeInputStream fis = new FakeInputStream();
    FakeSocket fs;
    SocketListener sl;
    String[] outStrings = {"test1","test2","test3","asdsdfsdf","asdsihweh"};
    
    public SocketListenerTest() throws UnsupportedEncodingException {
        this.fs = new FakeSocket(fis);
        this.sl = new SocketListener(fs);
    }
    
    @Test
    public void testFakeInputStream() throws IOException {
        DataInputStream dis =new DataInputStream(fis);
        assertTrue(fis.available()==0);
        for(String s:outStrings){
            fis.setInput(s);
            assertTrue(dis.available()>0);
            byte[] response=new byte[s.length()];
            dis.read(response);
            assertArrayEquals(response,s.getBytes());
        }
        assertTrue(fis.available()==0);
    }

    @Test
    public void testUpdate() throws UnsupportedEncodingException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(sl);
        fis.setInput("ID=0\r\n");
        Thread.sleep(300);
        assertEquals(((Transport)sl.listener).getID(), 0);
        ((Transport)sl.listener).addListener(tl);
        for(String s:outStrings){
            fis.setInput(s);
            Thread.sleep(300);
            assertEquals(tl.getData().getResponse(),s);
        }
    }
    
}
