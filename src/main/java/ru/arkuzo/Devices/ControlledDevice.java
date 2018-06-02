package ru.arkuzo.Devices;

import ru.arkuzo.Devices.Data.DeviceData;
import ru.arkuzo.core.Observer;

public interface ControlledDevice extends Observer {

    void enable();

    void disable();

    void set (DeviceData dataSet);

    DeviceData state();

    String toString();
}
