package ru.arkuzo.Transport;

import ru.arkuzo.Servers.SocketData;
import ru.arkuzo.core.Observable;
import ru.arkuzo.core.Observer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KeyboardListener extends Thread implements Observable{
    private BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in));
    private String inputString;
    private Observer listener;

    public KeyboardListener(Observer listener) {
        this.listener = listener;
    }

    @Override
    public void run(){
        while(true){
            try {
                if(br.ready()){
                    inputString=br.readLine();
                    inputString=inputString+"+";
                    notifyListeners();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

    @Override
    public void notifyListeners() {
        listener.handleEvent(new SocketData(inputString));
    }

    @Override
    public void addListener(Observer listener) {
        this.listener=listener;
    }

    @Override
    public void removeListener(Observer listener) {
        this.listener=null;
    }
}
