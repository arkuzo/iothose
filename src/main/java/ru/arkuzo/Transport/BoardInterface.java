/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.arkuzo.Transport;

import ru.arkuzo.core.Observable;
import ru.arkuzo.core.Observer;

/**
 *
 * @author arseniy
 */
public interface BoardInterface extends Observable, Observer {
    int getId();
}
