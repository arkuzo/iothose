package Transport.Digital;

import Servers.SocketData;
import Transport.pinMode;
import core.*;
import java.util.LinkedList;

public class DigitalPin implements Observer, Observable {
    private int number;
    private pinMode mode;
    private Data tempData;
    private final LinkedList<Observer> listeners = new LinkedList<>();
    private DigitalPinData data;

    public DigitalPin(int number) {
        data = new DigitalPinData(pinState.LOW);
        this.number=number;
    }    
    
    @Override
    public void handleEvent(Data data) {
        if(data instanceof SocketData){
            parseInput((SocketData)data);
            this.update();
        }
    }

    @Override
    public void update() {
        listeners.forEach((o) -> {
            o.handleEvent(tempData);
        });
    }

    @Override
    public void addListener(Observer listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Observer listener) {
        listeners.remove(listener);
    }
    
    private void parseInput(SocketData data) {
        String response = data.getResponse();
        if(response.matches("(?s)DCH=\\d{1,2},\\d.+")){
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
            valstr=response.substring(start, 1+start);
            i=Integer.parseInt(istr);
            value=Integer.parseInt(valstr);
            if(i!=number){
                return;
            }
            try{
                setDigitalData(value);
            } catch(IllegalArgumentException e){
                System.err.println(e);
            }
        }
    }

    private void setDigitalData(int value) {
        switch (value) {
            case 0:
                data.update(pinState.LOW);
                break;
            case 1:
                data.update(pinState.HIGH);
                break;
            default:
                throw new IllegalArgumentException("Invalid state for digital pin");
        }
    }

    public DigitalPinData getData() {
        return data;
    }
}
