package org.amoseman.audiosynth.device.devices;

import org.amoseman.stuff.projects.audiosynth.StereoSignal;
import org.amoseman.stuff.projects.audiosynth.TimeData;
import org.amoseman.stuff.projects.audiosynth.device.Device;

public class LowPassFilter extends Mixer {
    private final int poles = 4;
    private final double cutoff;
    private final double rc;
    private StereoSignal[] previous;

    public LowPassFilter(double cutoff) {
        this.cutoff = cutoff;
        this.rc = 1.0 / (cutoff * 2.0 * Math.PI);
        this.previous = new StereoSignal[poles];
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
        propogateNotes(notes);
        //double a = timeData.deltaTime / (rc + timeData.deltaTime);
        double a = 1.0 - Math.exp(-2.0 * Math.PI * cutoff * timeData.deltaTime);
        StereoSignal x = sumDeviceSignals(timeData);
        for (int i = 0 ; i < poles; i++) {
            x.scale(a);
            if (previous[i] != null) {
                x.add(previous[i].scale(1.0 - a));
            }
            previous[i] = x;
        }
        return x;
    }
}
