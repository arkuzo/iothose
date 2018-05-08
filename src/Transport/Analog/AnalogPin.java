package Transport.Analog;

import DatabaseHandlers.EventWriter;
import Sensors.data.Voltage;
import Servers.SocketData;
import com.sun.media.sound.InvalidDataException;
import core.*;
import java.util.LinkedList;

public class AnalogPin implements Observer, Observable {
    private final int id;
    private final Voltage voltage = new Voltage(0);
    private LinkedList<Observer> listeners = new LinkedList();
    private final int number;
    private final int resolution;
    private final float reference;

    public AnalogPin(int id,int number, int resolution, float reference) {
        this.number = number;
        this.resolution = resolution;
        this.reference = reference;
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
            EventWriter.write(this.toString());
        }
    }

    public Voltage getVoltage() {
        return voltage;
    }

    public int getId() {
        return id;
    }
    
}
