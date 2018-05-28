/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.arkuzo.Servers;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 *
 * @author arseniy
 */
public class FakeInputStream extends InputStream{
    private byte[] input = new byte[0];
    private int cursor=0;

    @Override
    public int available() throws IOException {
        if(input!=null){
            if(cursor<input.length)
                return input.length-cursor;
        }
        return 0;
    }

    @Override
    public int read() throws IOException {
        if(cursor>=input.length){
            cursor=0;
            input=null;
            return -1;
        }
        return input[cursor++];
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if(input==null)
            return 0;
        if(off+len>=input.length)
            len=input.length-off;
        ArrayList<Byte> al = new ArrayList();
        for(cursor=0;cursor<len;cursor++){
            al.add(input[off+cursor]);
        }
        for(cursor=0;cursor<len;cursor++){
            b[cursor]=al.get(cursor);
        }
        for(;cursor<b.length;cursor++){
            b[cursor]=0;
        }
        int oldCursor=cursor;
        input=null;
        cursor=0;
        return oldCursor;
    }
    
    
    
    public void setInput (String toInsert) throws UnsupportedEncodingException{
        input=toInsert.getBytes("ASCII");
    }
}
