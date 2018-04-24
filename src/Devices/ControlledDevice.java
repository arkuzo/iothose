package Devices;

import Devices.Data.DeviceData;
import core.Observer;

public interface ControlledDevice extends Observer {

    void enable();

    void disable();

    void set (DeviceData dataSet);

    DeviceData state();

    String toString();
}
