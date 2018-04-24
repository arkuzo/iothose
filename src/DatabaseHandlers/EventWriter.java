/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseHandlers;

import core.Launcher;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author arseniy
 */
public class EventWriter {
    
    private static EventWriter ew = new EventWriter();
    
    private EventWriter(){
        
    }
    
    synchronized public static void write(String message){
        try{
            String sqlRequest = "INSERT INTO event_log (event_desc) VALUES (?)";
            PreparedStatement stmt = Launcher.getDbConnection().prepareStatement(sqlRequest);
            System.out.println("Preparing statement");
            stmt.setString(1, message);
            System.out.println("Sending "+message+" to event log");
            stmt.execute(); 
            System.out.println("Sent");
        } catch (SQLException ex) {
            System.err.println(ex);
            //TODO fatal error logging
        }
    }
}
