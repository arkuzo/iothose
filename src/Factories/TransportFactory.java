/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factories;

import DatabaseHandlers.EventWriter;
import Transport.Arduino;
import Transport.Transport;
import core.Launcher;
import java.io.*;
import java.net.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author arseniy
 */

public class TransportFactory{
    
    private static LinkedList<Transport> boards = new LinkedList();
    private static TransportFactory factory;
    
    private TransportFactory() {
        try{
        getTransportFromDb();
        } catch(SQLException e) {
            boards.add(new Arduino(0, "test"));
            Launcher.stop();
        }
    }
    
    public static void init() throws SQLException{
        factory=TransportFactory.getFactory();
    }
    
    public static synchronized TransportFactory getFactory() throws SQLException{
        if (factory == null){
            factory = new TransportFactory();
        }
        return factory;
    }
    
    public static Transport getTransport (int id) throws UnknownHostException, IOException{
        for (Transport b : boards) {
            if(b.getID()==id){
                EventWriter.write("returning "+b.toString());
                return b;
            }
        }
        Transport b = (Transport) new Arduino(id,"test");
        boards.add(b);
        return b;
    }
    
    private void getTransportFromDb() throws SQLException{
        PreparedStatement stmt = Launcher.getDbConnection().
                prepareCall("SELECT id,description "
                        + "FROM transport");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String description = rs.getString("description");
            boards.add(new Arduino(id, description));
        }
        EventWriter.write("Loaded boards from db");
        StringBuffer sb = new StringBuffer();
        for(Transport b:boards){
            sb.append(b+"\r\n");
        }
        EventWriter.write(sb.toString());
    }
    
    private void addTransportToDb(){
        
    }
    
}
