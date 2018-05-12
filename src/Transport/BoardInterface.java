/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transport;

import core.Observable;
import core.Observer;

/**
 *
 * @author arseniy
 */
public interface BoardInterface extends Observable, Observer {
    int getId();
}
