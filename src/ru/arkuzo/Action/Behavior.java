/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.arkuzo.Action;

import ru.arkuzo.Devices.Data.LEDData;
import ru.arkuzo.core.Data;

/**
 *
 * @author arseniy
 */
interface Behavior {
    
    Data compute(Data inputData);
    
}
