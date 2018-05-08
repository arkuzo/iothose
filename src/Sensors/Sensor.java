package Sensors;

import Sensors.data.SensorData;
import core.Observable;
import core.Observer;

public interface Sensor extends Observable, Observer{


    SensorData read();

    void calibrate(Object calibrationMatrix);
}