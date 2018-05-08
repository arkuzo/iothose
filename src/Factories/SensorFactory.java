/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factories;

import DatabaseHandlers.EventWriter;
import Sensors.AmperkaLuminocityResistor;
import Sensors.Sensor;
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
public class SensorFactory {
    private static LinkedList<Sensor> sensors = new LinkedList();
    private static SensorFactory factory;
    
    private SensorFactory() throws SQLException, IOException {
        getPinsFromDb();
    }
    
    public static void init() throws SQLException, IOException{
        factory=new SensorFactory();
    }
    
    public static synchronized SensorFactory getFactory() throws SQLException, IOException{
        if (factory == null){
            factory = new SensorFactory();
        }
        return factory;
    }    

    private void getPinsFromDb() throws SQLException, IOException {
        PreparedStatement stmt = Launcher.getDbConnection().
                prepareCall("SELECT * "
                        + "FROM sensor");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            int pinId = rs.getInt("pin_id");
            String description = rs.getString("description");
            String type = rs.getString("type");
            Sensor sensor=null;
            switch(type){
                case "AmperkaLuminocityResistor":
                    sensor = new AmperkaLuminocityResistor(description);
                    break;
                default:
                    break;
            }
            if(sensor!=null){
                sensors.add(sensor);
            } else {
                throw new RuntimeException("Unknown sensor type in database");
            }
            PinFactory.getPinById(pinId).addListener(sensor);
        }
        EventWriter.write("Loaded sensors from db");
    }
}
