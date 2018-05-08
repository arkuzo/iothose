/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factories;

import DatabaseHandlers.EventWriter;
import Transport.Analog.AnalogPin;
import Transport.Digital.DigitalPin;
import Transport.Pin;
import Transport.pinMode;
import core.Launcher;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author arseniy
 */
public class PinFactory {
    
    private static LinkedList<Pin> pins = new LinkedList();
    private static PinFactory factory;
    
    private PinFactory() throws SQLException, IOException {
        getPinsFromDb();
    }
    
    public static void init() throws SQLException, IOException{
        factory=new PinFactory();
    }
    
    public static synchronized PinFactory getFactory() throws SQLException, IOException{
        if (factory == null){
            factory = new PinFactory();
        }
        return factory;
    }    

    private void getPinsFromDb() throws SQLException, IOException {
        PreparedStatement stmt = Launcher.getDbConnection().
                prepareCall("SELECT * "
                        + "FROM pin");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Pin pin;
            int id = rs.getInt("id");
            int number = rs.getInt("number");
            int trasnportId = rs.getInt("transport_id");
            String type = rs.getString("type");
            switch (type){
                case "analog":
                    int resolution = rs.getInt("resolution");
                    float reference = rs.getFloat("reference_voltage");
                    pin = new AnalogPin(id,number,resolution,reference);
                    break;
                case "digital":
                    boolean pinIsInput = rs.getBoolean("is_input");
                    pinMode mode=null;
                    if(pinIsInput){
                        mode = pinMode.INPUT;
                    } else {
                        mode = pinMode.OUTPUT;
                    }
                    pin=new DigitalPin(id, number, mode);
                    break;
                default:
                    throw new SQLDataException("FATAL Cannot recognize pin #"+id+" mode");
            }
            pins.add(pin);
            TransportFactory.getTransport(trasnportId).addListener(pin);
        }
        EventWriter.write("Loaded pins from db");
    }
    
    static Pin getPinById(int id){
        Pin returnPin=null;
        for(Pin a:pins){
            if(a.getId()==id)
                returnPin=a;
        }
        if(returnPin!=null)
            return returnPin;
        else
            throw new NullPointerException("Pin with id "+id+"not found in database");
    }
}
