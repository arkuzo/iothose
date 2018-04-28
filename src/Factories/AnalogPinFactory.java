/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factories;

import DatabaseHandlers.EventWriter;
import Transport.Analog.AnalogPin;
import core.Launcher;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author arseniy
 */
public class AnalogPinFactory {
    
    private static LinkedList<AnalogPin> pins = new LinkedList();
    private static AnalogPinFactory factory;
    
    private AnalogPinFactory() throws SQLException, IOException {
        getPinsFromDb();
    }
    
    public static void init() throws SQLException, IOException{
        factory=new AnalogPinFactory();
    }
    
    public static synchronized AnalogPinFactory getFactory() throws SQLException, IOException{
        if (factory == null){
            factory = new AnalogPinFactory();
        }
        return factory;
    }    

    private void getPinsFromDb() throws SQLException, IOException {
        PreparedStatement stmt = Launcher.getDbConnection().
                prepareCall("SELECT * "
                        + "FROM analog_pin");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            int number = rs.getInt("number");
            int resolution = rs.getInt("resolution");
            float reference = rs.getFloat("reference_voltage");
            int trasnportId = rs.getInt("transport_id");
            AnalogPin pin = new AnalogPin(id,number,resolution,reference);
            pins.add(pin);
            TransportFactory.getTransport(trasnportId).addListener(pin);
        }
        EventWriter.write("Loaded analog pins from db");
    }
    
}
