/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.arkuzo.Servers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.Socket;

/**
 *
 * @author arseniy
 */
public class FakeSocket extends Socket {
    FakeInputStream fis;
    FakeOutputStream fos = new FakeOutputStream();

    public FakeSocket(FakeInputStream fis) {
        this.fis = fis;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return fos; //To change body of generated methods, choose Tools | Templates.
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

    public FakeOutputStream getFos() {
        return fos;
    }
    
}
