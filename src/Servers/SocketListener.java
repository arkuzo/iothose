package Servers;

import DatabaseHandlers.EventWriter;
import core.Observable;
import core.Observer;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class SocketListener extends Thread implements Observable {
    private Observer listener;
    private Socket toListen;
    private DataInputStream socketInput;
    private byte[] response=new byte[2048];
    private int length;

    public SocketListener(Observer listener, Socket toListen) {
        this.listener = listener;
        this.toListen = toListen;
        try{
            socketInput = new DataInputStream(toListen.getInputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    

    public void run(){
        //EventWriter.write("Started listen from "+toListen.getInetAddress()+" in thread "
        //        +currentThread().getName());
        while(!currentThread().isInterrupted()) {
            try {
                if (socketInput.available() > 0) {
                    length=socketInput.available();
                    //EventWriter.write("Something received, gonna check it");
                    socketInput.read(response);
                    update();
                }
            } catch (IOException e) {
                EventWriter.write(e.toString());
            }
        }
    }

    @Override
    public void update() {
        try {
            listener.handleEvent(new SocketData(new String(response,0,length,"ASCII")));
            //EventWriter.write("Sent " + new String(response,0,length,"ASCII") + " to observer");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addListener(Observer listener) {
        this.listener=listener;
    }

    @Override
    public void removeListener(Observer listener) {
        this.listener=null;
    }
}
