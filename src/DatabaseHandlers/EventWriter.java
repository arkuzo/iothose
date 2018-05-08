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
            errorMessage(ex, message);
            //TODO fatal error logging
        } catch (NullPointerException ex){
            errorMessage(ex, message);
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
            errorMessage(ex, message);
            //TODO fatal error logging
        } catch (NullPointerException ex){
            errorMessage(ex, message);
        }
    }
    
    private static void errorMessage (Exception ex, String message){
        System.err.println( ex.toString() + " while writing \"" + message + "\"");
    }
    
    public static void sensorMessage(float scale, String unit){
        try{
            String sqlRequest = "INSERT INTO sensor_log (scale,unit) VALUES (?,?)";
            PreparedStatement stmt = Launcher.getDbConnection().prepareStatement(sqlRequest);
            stmt.setDouble(1, scale);
            stmt.setString(2, unit);
            stmt.execute(); 
        } catch (SQLException ex) {
            errorMessage(ex,"Cannot write to db");
            //TODO fatal error logging
        } catch (NullPointerException ex){
            errorMessage(ex, "Cannot write to db");
        }
    }
}
