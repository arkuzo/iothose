package ru.arkuzo.Transport.Digital;

import ru.arkuzo.Servers.SocketData;
import ru.arkuzo.Transport.pinMode;
import java.util.LinkedList;
import ru.arkuzo.Transport.BoardInterface;
import ru.arkuzo.core.Data;
import ru.arkuzo.core.Observable;
import ru.arkuzo.core.Observer;

public class DigitalPin implements Observer, Observable, BoardInterface {
    private int number;
    private int id;
    private pinMode mode;
    private Data tempData;
    private final LinkedList<Observer> listeners = new LinkedList<>();
    private DigitalPinData data;
    private boolean updated=false;

    public DigitalPin(int id, int number, pinMode mode) {
        this.id = id;
        data = new DigitalPinData(pinState.LOW);
        this.number=number;
        this.mode = mode;
    }    
    
    @Override
    public void handleEvent(Data data) {
        if(data instanceof SocketData){
            parseInput((SocketData)data);
        }
        if(this.updated){
            this.notifyListeners();
            this.updated=false;
        }
    }

    @Override
    public void notifyListeners() {
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
                this.updated=true;
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

    @Override
    public int getId() {
        return id;
    }
}
