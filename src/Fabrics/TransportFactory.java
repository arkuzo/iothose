/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fabrics;

import Transport.Arduino;
import Transport.Transport;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author arseniy
 */
public class TransportFactory{
    
    static LinkedList<Transport> boards = new LinkedList();
    private static TransportFactory factory;
    private BufferedWriter writer;
    private int port;
    
    private TransportFactory() {
    }
    
    public static synchronized TransportFactory getFactory(){
        if (factory == null){
            factory = new TransportFactory();
        }
        return factory;
    }
    
    public static Transport getTransport (int id) throws UnknownHostException, IOException{
        for (Transport b : boards) {
            if(b.getID()==id){
                return b;
            }
        }
        Transport b = (Transport) new Arduino(id,"test");
        boards.add(b);
        return b;
    }
    
    private void getTransportFromDb(){
        
    }
    
    private void addTransportToDb(){
        
    }
    
}
