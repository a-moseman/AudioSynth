package org.amoseman.audiosynth.device.devices;

import org.amoseman.audiosynth.StereoSignal;
import org.amoseman.audiosynth.TimeData;
import org.amoseman.audiosynth.device.Device;

public class LowPassFilter extends Mixer {
    private final double cutoff;
    private final double rc;
    private StereoSignal previous;

    public LowPassFilter(double cutoff) {
        this.cutoff = cutoff;
        this.rc = 1.0 / (cutoff * 2.0 * Math.PI);
        this.previous = StereoSignal.zero();
    }

    public static LowPassFilter create(double cutoff) {
        return new LowPassFilter(cutoff);
    }

    @Override
    public LowPassFilter connectDevice(double amplitude, Device device) {
        return (LowPassFilter) super.connectDevice(amplitude, device);
    }

    @Override
    public StereoSignal process(TimeData timeData) {
        double a = timeData.deltaTime / (rc + timeData.deltaTime);

        propogateNotes(notes);
        StereoSignal x = sumDeviceSignals(timeData);
        StereoSignal y = x.copy().scale(a).add(previous.copy().scale(1.0 - a));
        previous = y;
        return y;
    }
}
