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

    public static void writeError(String message) {
        try{
            String sqlRequest = "INSERT INTO event_log (event_desc,type) VALUES (?,?)";
            PreparedStatement stmt = Launcher.getDbConnection().prepareStatement(sqlRequest);
            stmt.setString(1, message);
            stmt.setString(2, "error");
            stmt.execute(); 
        } catch (SQLException ex) {
            System.err.println(ex);
            //TODO fatal error logging
        }
    }
    
    private EventWriter(){
        
    }
    
    synchronized public static void write(String message){
        try{
            String sqlRequest = "INSERT INTO event_log (event_desc) VALUES (?)";
            PreparedStatement stmt = Launcher.getDbConnection().prepareStatement(sqlRequest);
            stmt.setString(1, message);
            stmt.execute(); 
        } catch (SQLException ex) {
            System.err.println(ex);
            //TODO fatal error logging
        }
    }
}
