/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servers;

import DatabaseHandlers.EventWriter;
import core.Launcher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author arseniy
 */
public class CommandLineListener implements Runnable {
    private static CommandLineListener cll=null;
    private static BufferedReader br;

    private CommandLineListener() {
            br=new BufferedReader(new InputStreamReader(System.in));
    }
    
    public static synchronized CommandLineListener getListener(){
        if(cll==null){
            cll = new CommandLineListener();
        }
        return cll;
    }

    @Override
    public void run() {
        System.out.println("You can enter commands here:");
        while(!Thread.interrupted()){
            String input;
            try {
                input = br.readLine();
            } catch (IOException ex) {
                EventWriter.writeError(ex+"\nCommand line listener disabled");
                return;
            }
            parse(input);
        }
    }

    private void parse(String input) {
        switch(input){
            case "exit": Launcher.stop();
        }
    }
    
}
