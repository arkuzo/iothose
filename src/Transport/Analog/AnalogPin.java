package Transport.Analog;

import Sensors.data.Voltage;
import Servers.SocketData;
import com.sun.media.sound.InvalidDataException;
import core.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalogPin implements Observer, Observable {
    private final Voltage voltage = new Voltage(0);
    private LinkedList<Observer> listeners = new LinkedList();
    private int number;
    private int resolution;
    private float reference;

    public AnalogPin(Voltage voltage, int number, int resolution, float reference) {
        this.voltage.update(voltage.getScale());
        this.number = number;
        this.resolution = resolution;
        this.reference = reference;
    }

    

    @Override
    public void handleEvent(Data data) {
        if(data instanceof SocketData){
            try {
                parseInput((SocketData)data);
            } catch (InvalidDataException ex) {
                System.err.println(ex);
            }
        }
        this.update();
    }

    @Override
    public void update() {
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
        if(response.matches("(?s)ACH=\\d{1,2},\\d{3,5}.+")){
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
            while(Character.isDigit(response.charAt(start+radix))){
                radix++;
            }
            valstr=response.substring(start, radix+start);
            i=Integer.parseInt(istr);
            value=Integer.parseInt(valstr);
            if(i!=number){
                return;
            }
            if(value>(float)Math.pow(2,resolution))
                throw new InvalidDataException("Analog value failure");
            this.voltage.update(value/(float)Math.pow(2,resolution)*reference);
        }
    }

    public Voltage getVoltage() {
        return voltage;
    }
    
}
