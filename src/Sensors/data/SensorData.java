package Sensors.data;

import core.Data;

public interface SensorData extends Data {

    double getScale();

    //todo remove scale from signature when hardware will be present
    void update (double scale);

    String toString();

}
