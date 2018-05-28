/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.arkuzo.Servers;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author arseniy
 */
public class FakeOutputStream extends OutputStream {
    StringBuffer sb = new StringBuffer();

    @Override
    public void write(int b) throws IOException {
        byte ascii = (byte)b;
        System.out.println("Appending "+(char)ascii);
        sb.append((char)ascii);
    }
    
    
    
    public String getOutput(){
        String out = sb.toString();
        sb = new StringBuffer();
        return out;
    }
    
}
