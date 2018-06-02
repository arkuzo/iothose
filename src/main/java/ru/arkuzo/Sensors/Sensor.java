package ru.arkuzo.Sensors;

import ru.arkuzo.Sensors.data.SensorData;
import ru.arkuzo.core.Observable;
import ru.arkuzo.core.Observer;

public interface Sensor extends Observable, Observer{


    SensorData read();

    void calibrate(Object calibrationMatrix);
}