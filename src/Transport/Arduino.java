package Transport;

import Servers.SocketData;
import core.Data;
import core.Observer;
import java.util.LinkedList;


public class Arduino extends Thread implements Transport{
    
    protected int id;
    private final LinkedList<Observer> listeners = new LinkedList<>();
    private Data data;
    private String description;

    public Arduino(int id, String description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public int getID(){
        return id;
    }

    @Override
    public void notifyListeners() {
        listeners.forEach((x) -> x.handleEvent(data));
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
    public void handleEvent(Data data) {
        if(!(data instanceof SocketData))
            return;
        this.data=data;
        this.notifyListeners();
        //EventWriter.write("Arduino " + this.id +" reported "+response);
    }

    @Override
    public String toString() {
        return "Arduino "+String.valueOf(id)+' '+description;
    }
    
}
