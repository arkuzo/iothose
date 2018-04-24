/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import Devices.Data.LEDData;
import core.Data;

/**
 *
 * @author arseniy
 */
public class Behavior {
    Data inputData;
    Data outputData;
    
    Data compute(){
        return new LEDData();
    }
}
