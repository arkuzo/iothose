package Servers;

import DatabaseHandlers.EventWriter;
import core.Observable;
import core.Observer;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketListener extends Thread implements Observable {
    private Observer listener;
    private Socket toListen;
    private DataInputStream socketInput;
    private byte[] response=new byte[2048];
    private int length;
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public SocketListener(Observer listener, Socket toListen) {
        EventWriter.write("Creating listener from "+toListen.getInetAddress().getHostAddress());
        this.listener = listener;
        this.toListen = toListen;
        try{
            socketInput = new DataInputStream(toListen.getInputStream());
        } catch (IOException e){
            EventWriter.writeError("Error creating socketListener" + e.toString());
        }
    }
    
    

    public void run(){
        EventWriter.write("Started listen from "+toListen.getInetAddress()+" in thread "
                +currentThread().getName());
        while(!currentThread().isInterrupted()) {
            try {
                if (socketInput.available() > 0) {
                    length=socketInput.available();
                    //EventWriter.write("Something received, gonna check it");
                    socketInput.read(response);
                    notifyListeners();
                }
            } catch (IOException e) {
                EventWriter.write(e.toString());
            }
        }
    }

    @Override
    public void notifyListeners() {
        executorService.submit(()->{
            try {
                listener.handleEvent(new SocketData(new String(response,0,length,"ASCII")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        
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
