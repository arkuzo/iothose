/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servers;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 *
 * @author arseniy
 */
class FakeInputStream extends InputStream{
    private byte[] output = new byte[0];
    private int cursor=0;

    @Override
    public int available() throws IOException {
        if(output!=null){
            if(cursor<output.length)
                return output.length-cursor;
        }
        return 0;
    }

    @Override
    public int read() throws IOException {
        if(cursor>=output.length){
            cursor=0;
            output=null;
            return -1;
        }
        return output[cursor++];
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if(output==null)
            return 0;
        if(off+len>=output.length)
            len=output.length-off;
        ArrayList<Byte> al = new ArrayList();
        for(cursor=0;cursor<len;cursor++){
            al.add(output[off+cursor]);
        }
        for(cursor=0;cursor<len;cursor++){
            b[cursor]=al.get(cursor);
        }
        for(;cursor<b.length;cursor++){
            b[cursor]=0;
        }
        int oldCursor=cursor;
        output=null;
        cursor=0;
        return oldCursor;
    }
    
    
    
    public void setOutput (String toInsert) throws UnsupportedEncodingException{
        output=toInsert.getBytes("ASCII");
    }
}
