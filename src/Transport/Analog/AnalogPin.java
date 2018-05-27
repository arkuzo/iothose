package Transport.Analog;

import DatabaseHandlers.EventWriter;
import Sensors.data.Voltage;
import Servers.SocketData;
import com.sun.media.sound.InvalidDataException;
import core.*;
import java.util.LinkedList;
import Transport.BoardInterface;

public class AnalogPin implements Observer, Observable, BoardInterface {
    private final int id;
    private final Voltage voltage = new Voltage(0);
    private LinkedList<Observer> listeners = new LinkedList();
    private final int number;
    private final int resolution;
    private final Voltage reference;
    private boolean updated=false;

    public AnalogPin(int id,int number, int resolution, float reference) {
        this.number = number;
        this.resolution = resolution;
        this.reference = new Voltage(reference);
        this.id=id;
    }

    

    @Override
    public void handleEvent(Data data) {
        if(data instanceof SocketData){
            try {
                parseInput((SocketData)data);
            } catch (InvalidDataException ex) {
                EventWriter.writeError(ex.toString());
            }
        }
        if(this.updated){
            this.notifyListeners();
            updated=false;
        }
    }

    @Override
    public void notifyListeners() {
        listeners.forEach((a) -> a.handleEvent(voltage));
    }

    @Override
    public void addListener(Observer listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Observer listener) {
        listeners.remove(listener);
    }    

    @Override
    public String toString() {
        return "Analog pin " +number + ' ' + voltage; //To change body of generated methods, choose Tools | Templates.
    }

    private void parseInput(SocketData data) throws InvalidDataException {
        String response = data.getResponse();
        if(response.matches("(?s)ACH=\\d{1,2},\\d{1,5}.+")){
            String istr, valstr;
            int i, value,start,radix=0;
            if(Character.isDigit(response.charAt(5))){
                istr=response.substring(4, 6);
            }
            else{
                istr=response.substring(4,5);
            }
            if(Character.isDigit(response.charAt(6))){
                start=6;
            }
            else{
                start=7;
            }
            valstr=response.substring(start).trim();
            i=Integer.parseInt(istr);
            value=Integer.parseInt(valstr);
            if(i!=number){
                return;
            }
            if(value>(float)Math.pow(2,resolution))
                throw new InvalidDataException("Analog value failure");
            this.voltage.update(value/(float)Math.pow(2,resolution)
                    *reference.getScale());
            EventWriter.write(this.toString());
            updated = true;
        }
    }

    public Voltage getVoltage() {
        return voltage;
    }

    public int getId() {
        return id;
    }

    public Voltage getReferenceVoltage() {
        return reference;
    }
    
    
    
}
