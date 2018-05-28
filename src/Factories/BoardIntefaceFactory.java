/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Factories;

import DatabaseHandlers.EventWriter;
import Transport.Analog.AnalogPin;
import Transport.Digital.DigitalPin;
import Transport.pinMode;
import core.Launcher;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import Transport.BoardInterface;

/**
 *
 * @author arseniy
 */
public class BoardIntefaceFactory {
    
    private static LinkedList<BoardInterface> boardInterfaces = new LinkedList();
    private static BoardIntefaceFactory factory;
    
    private BoardIntefaceFactory() throws SQLException, IOException {
        getBoardInterfacesFromDb();
    }
    
    public static void init() throws SQLException, IOException{
        factory=new BoardIntefaceFactory();
    }
    
    public static synchronized BoardIntefaceFactory getFactory() throws SQLException, IOException{
        if (factory == null){
            factory = new BoardIntefaceFactory();
        }
        return factory;
    }    

    private void getBoardInterfacesFromDb() throws SQLException, IOException {
        PreparedStatement stmt = Launcher.getDbConnection().
                prepareCall("SELECT * "
                        + "FROM board_interface");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            BoardInterface bInterface;
            int id = rs.getInt("id");
            int number = rs.getInt("number");
            int trasnportId = rs.getInt("transport_id");
            String type = rs.getString("type");
            switch (type){
                case "analog":
                    int resolution = rs.getInt("resolution");
                    float reference = rs.getFloat("reference_voltage");
                    bInterface = new AnalogPin(id,number,resolution,reference);
                    break;
                case "digital":
                    boolean pinIsInput = rs.getBoolean("is_input");
                    pinMode mode=null;
                    if(pinIsInput){
                        mode = pinMode.INPUT;
                    } else {
                        mode = pinMode.OUTPUT;
                    }
                    bInterface=new DigitalPin(id, number, mode);
                    break;
                default:
                    throw new SQLDataException("FATAL Cannot recognize board "
                            + "interface id:"+id);
            }
            boardInterfaces.add(bInterface);
            try {
                TransportFactory.getTransport(trasnportId).addListener(bInterface);
            } catch (TransportNotFoundException ex) {
                EventWriter.writeError("Board interface id " + id 
                        + "is configured to work with transport id "+ trasnportId +
                        " that cannot be found");
            }
        }
        EventWriter.write("Loaded board interfaces from db");
    }
    
    static BoardInterface getInterfaceById(int id){
        BoardInterface returnIface=null;
        for(BoardInterface a:boardInterfaces){
            if(a.getId()==id)
                returnIface=a;
        }
        if(returnIface!=null)
            return returnIface;
        else
            throw new NullPointerException("Board interface with id "+id+"not found in database");
    }
}
