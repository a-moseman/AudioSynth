package org.amoseman.audiosynth.device.devices;

import org.amoseman.stuff.projects.audiosynth.StereoSignal;
import org.amoseman.stuff.projects.audiosynth.TimeData;
import org.amoseman.stuff.projects.audiosynth.device.Device;

public class Reverb extends Mixer {
    private static final int BUFFER_SIZE = 1024 * 30;
    private static final int DELAY = 1024 * 10;
    private final StereoSignal[] reverbBuffer;
    private int position;

    public Reverb() {
        this.reverbBuffer = new StereoSignal[BUFFER_SIZE];
        this.position = 0;
    }

    public static Reverb create() {
        return new Reverb();
    }

    @Override
    public Reverb connectDevice(double amplitude, Device device) {
        return (Reverb) super.connectDevice(amplitude, device);
    }

    @Override
    public StereoSignal process(TimeData timeData) {
        // get input signal from devices
        propogateNotes(notes);
        StereoSignal signal = sumDeviceSignals(timeData);
        if (position == reverbBuffer.length) {
            position = 0;
        }
        reverbBuffer[position] = signal;
        position++;
        StereoSignal out = signal.copy();
        if (position == 1) {
            return out;
        }
        int k = position - 2 - DELAY;
        if (k < 0) {
            k += reverbBuffer.length;
        }
        StereoSignal previous = reverbBuffer[k];
        if (previous == null) {
            return out;
        }
        out.add(previous.copy().scale(0.5));
        return out;
    }
}
