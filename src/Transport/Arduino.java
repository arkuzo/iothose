package Transport;

import DatabaseHandlers.EventWriter;
import Servers.SocketData;
import Servers.SocketListener;
import Servers.TransportServer;
import core.Data;
import core.Observable;
import core.Observer;

import java.net.*;
import java.io.*;
import java.util.LinkedList;


public class Arduino extends Thread implements Observer, Observable, Transport{
    
    protected Socket socket;
    protected int id;
    protected String response;
    protected DataOutputStream socketOutput;
    protected SocketListener socketListener;
    private LinkedList<Observer> listeners = new LinkedList<>();
    private Data data;
    private String description;

    public Arduino(int id, String description) {
        this.id = id;
        this.description = description;
        response = "ERROR";
        EventWriter.write("Created Arduino id "+id);
    }

    @Override
    public final void setSocket(Socket socket) {
        socketListener.removeListener(this);
        socketListener = new SocketListener(this,socket);
        this.socket = socket;
        try{
            socketOutput = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
        TransportServer.startListen(socketListener);
    }

    @Override
    public int getID(){
        return id;
    }

    public Arduino(int id, Socket socket, String description) throws UnknownHostException {
        this(id, description);
        this.setSocket(socket);
    }

    void send(String string){
        byte[] message;
        try {
            message = string.getBytes("ASCII");
            socketOutput.write(message);
            socketOutput.flush();
            //EventWriter.write("sent message "+ string);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean isActive(){
        send("AT\r\n");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        return response.matches("\\s+OK\\s+");
    }

    @Override
    public void update() {
        listeners.forEach((x) -> x.handleEvent(data));
    }

    @Override
    public void addListener(Observer listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Observer listener) {
        listeners.remove(listener);
    }  

    @Override
    public void handleEvent(Data data) {
        if(!(data instanceof SocketData))
            return;
        this.data=data;
        SocketData newData = (SocketData) data;
        this.response=newData.getResponse();
        //EventWriter.write("Arduino " + this.id +" reported "+response);
    }

}
