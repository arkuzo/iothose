package ru.arkuzo.Servers;

import ru.arkuzo.DatabaseHandlers.EventWriter;
import ru.arkuzo.Factories.TransportFactory;
import ru.arkuzo.Factories.TransportNotFoundException;
import ru.arkuzo.Transport.Transport;
import ru.arkuzo.core.Observable;
import ru.arkuzo.core.Observer;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketListener extends Thread implements Observable {
    Observer listener;
    private Socket toListen;
    private DataInputStream socketInput;
    private byte[] response=new byte[2048];
    private int length;
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public SocketListener(Socket toListen) {
        EventWriter.write("Creating listener from "+toListen.getInetAddress().getHostAddress());
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
        try {
            initializeTransport();
        } catch (Exception ex) {
            EventWriter.writeError(ex.toString() + " while trying to init transport at "
                    +toListen.getInetAddress().toString());
            return;
        }
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
    
    private void initializeTransport() throws IOException{
        while(socketInput.available()==0);
        length=socketInput.available();
        socketInput.read(response);
        String idReport = new String(response,0,length,"ASCII");
        EventWriter.write("Received "+idReport.trim()+" from "+
                toListen.getInetAddress().toString());
        if(!idReport.matches("(?s)ID=\\d+.+")){
            toListen.close();
            throw new IllegalArgumentException("Illegal id report: "+idReport.trim());
        }
        String idString = idReport.substring(3).trim();
        int id = Integer.parseInt(idString);
        Transport transport;
        try {
            transport = TransportFactory.getTransport(id);
            this.addListener(transport);
        } catch (TransportNotFoundException ex) {
            EventWriter.writeError("Transport id "+id+" not found");
            throw new IOException();
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
