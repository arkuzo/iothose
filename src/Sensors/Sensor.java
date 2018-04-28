package Sensors;

import Sensors.data.SensorData;
import core.Observable;

public interface Sensor extends Observable {

    void update();

    SensorData read();

    void enable();

    void calibrate(Object calibrationMatrix);
}