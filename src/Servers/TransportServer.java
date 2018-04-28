/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servers;

import DatabaseHandlers.EventWriter;
import Factories.TransportFactory;
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
    static private ServerSocket ss=null;

    private TransportServer(int port) throws ServerException, InterruptedException {
        boolean error=true;
        this.port = port;
        for(int i=0; i<5 && error; i++){
            try {
                ss = new ServerSocket(port);
                error=false;
            } catch (IOException ex) {
                EventWriter.writeError("Cannot open port "+port+" to serve transport");
                Thread.sleep(1000);
            }
        }
        if(error)throw new ServerException("Cannot open port "+port+" to serve transport");
    }
    
    static synchronized public TransportServer getServer() throws ServerException, InterruptedException{
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
        EventWriter.write("Started listen on port "+port+" to serve transport");
        while(!Thread.currentThread().isInterrupted()){
            Socket socket;
            try{ 
                socket = ss.accept();
                EventWriter.write("Connection with " + 
                        socket.getInetAddress().getHostAddress() +"established");
            } catch (IOException ex) {
                EventWriter.writeError("Transport connection failed");
                continue;
            }
            try {
                TransportFactory.getTransport(0).setSocket(socket);
                EventWriter.write("Set socket for transport");
            } catch (Exception ex) {
                EventWriter.writeError(ex.toString());
            }
        }
        EventWriter.write("Transport server closed");
    }
}
