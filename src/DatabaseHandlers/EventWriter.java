/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseHandlers;

import core.Launcher;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author arseniy
 */
public class EventWriter {
    
    private static EventWriter ew = new EventWriter();
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();
    
     private EventWriter(){
        
    }

    public static synchronized void writeError(String message) {
        executorService.submit(()->{
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
        });
        
    }
    
    synchronized public static void write(String message){
        executorService.submit(()->{
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
        });
    }
    
    private static void errorMessage (Exception ex, String message){
        System.err.println( ex.toString() + " while writing \"" + message + "\"");
    }
    
    public static synchronized void sensorMessage(int sensorId, double scale, String unit){
        executorService.submit(()->{
            try{
                String sqlRequest = "INSERT INTO sensor_log (sensor_id,scale,unit) VALUES (?,?,?)";
                PreparedStatement stmt = Launcher.getDbConnection().prepareStatement(sqlRequest);
                stmt.setInt(1, sensorId);
                stmt.setDouble(2, scale);
                stmt.setString(3, unit);
                stmt.execute(); 
            } catch (SQLException ex) {
                errorMessage(ex,"Cannot write to db");
                //TODO fatal error logging
            } catch (NullPointerException ex){
                errorMessage(ex, "Cannot write to db");
            }
        });   
    }
}
