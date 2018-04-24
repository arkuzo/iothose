/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servers;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.Socket;

/**
 *
 * @author arseniy
 */
public class FakeSocket extends Socket {
    FakeInputStream fis;

    public FakeSocket(FakeInputStream fis) {
        this.fis = fis;
    }

  
    
    @Override
    public InetAddress getInetAddress() {
        return InetAddress.getLoopbackAddress();
    }

    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return fis;
    }

    @Override
    public boolean isClosed() {
        return false;
    }
    
    public void send(){
       
    }
    
}
