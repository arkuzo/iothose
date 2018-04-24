/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servers;

import DatabaseHandlers.EventWriter;
import Fabrics.TransportFactory;
import core.Launcher;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author arseniy
 */
public class TransportServer implements Runnable {
    private static final ExecutorService socketExService =Executors.newCachedThreadPool();
    static private int port;
    static private TransportServer server=null;

    private TransportServer(int port) {
        this.port = port;
    }
    
    static synchronized public TransportServer getServer(){
        if(server==null){
            server = new TransportServer(Launcher.getTransportPort());
        }
        return server;
    }
    
    static public void startListen (SocketListener socketListener){
        socketExService.submit(socketListener);
    }
    
    @Override
    public void run() {
        ServerSocket ss=null;
        try {
            ss = new ServerSocket(port);
        } catch (IOException ex) {
            EventWriter.write("Cannot open port "+port+" to serve transport");
            ex.printStackTrace();
        }
        EventWriter.write("Started listen on port "+port+" to serve transport");
        while(!Thread.currentThread().isInterrupted()){
            Socket socket=null;
            try{ 
                socket = ss.accept();
                EventWriter.write("Connection with " + 
                        socket.getInetAddress().getHostAddress() +"established");
            } catch (IOException ex) {
                EventWriter.write("Transport connection failed");
            }
            try {
                TransportFactory.getTransport(0).setSocket(socket);
                EventWriter.write("Set socket for transport");
            } catch (Exception ex) {
                EventWriter.write(ex.toString());
            }
        }
        EventWriter.write("Transport server closed");
    }
}
