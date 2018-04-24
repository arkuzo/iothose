/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transport;

import java.net.Socket;

/**
 *
 * @author arseniy
 */
public interface Transport extends Runnable {
    
    public int getID();
    
    public void setSocket(Socket socket);
    
}
