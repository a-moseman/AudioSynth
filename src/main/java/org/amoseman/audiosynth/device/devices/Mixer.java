package org.amoseman.audiosynth.device.devices;

import org.amoseman.stuff.projects.audiosynth.StereoSignal;
import org.amoseman.stuff.projects.audiosynth.TimeData;
import org.amoseman.stuff.projects.audiosynth.device.Device;

public class Mixer extends Device {
    @Override
    public StereoSignal process(TimeData timeData) {
        return sumDeviceSignals(timeData);
    }

    public StereoSignal sumDeviceSignals(TimeData timeData) {
        StereoSignal signal = StereoSignal.zero();
        for (int i = 0; i < inputDevices.size(); i++) {
            signal.add(inputDevices.get(i).process(timeData).scale(inputAmplitudes.get(i)));
        }
        return signal;
    }
}
