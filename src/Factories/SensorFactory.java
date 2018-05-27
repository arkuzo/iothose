/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factories;

import DatabaseHandlers.EventWriter;
import Sensors.*;
import Sensors.data.*;
import Transport.Analog.AnalogPin;
import core.Launcher;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

/**
 *
 * @author arseniy
 */
public class SensorFactory {
    private static LinkedList<Sensor> sensors = new LinkedList();
    private static SensorFactory factory;
    
    private SensorFactory() throws SQLException, IOException {
        getSensorsFromDb();
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

    private void getSensorsFromDb() throws SQLException, IOException {
        PreparedStatement stmt = Launcher.getDbConnection().
                prepareCall("SELECT * "
                        + "FROM sensor");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            int pinId = rs.getInt("pin_id");
            String description = rs.getString("description");
            String type = rs.getString("type");
            double pulldown = rs.getDouble("pulldownResistor");
            Sensor sensor;
            switch(type){
                case "AmperkaLuminocitySensor":
                    AnalogPin ap = (AnalogPin)BoardIntefaceFactory.getInterfaceById(pinId);
                    sensor = new AmperkaLuminocitySensor(id,description,ap.getReferenceVoltage());
                    break;
                case "SNS-TMP10K":
                    AnalogPin ap1 = (AnalogPin)BoardIntefaceFactory.getInterfaceById(pinId);
                    sensor = new SnsTmp10KThermometer(
                        ap1.getReferenceVoltage(),new Resistance(pulldown),
                               id);
                    break;
                default:
                    throw new RuntimeException("Unknown sensor type in database");
            }
            sensors.add(sensor);
            BoardIntefaceFactory.getInterfaceById(pinId).addListener(sensor);
        }
        EventWriter.write("Loaded sensors from db");
    }
}
