package org.amoseman.audiosynth.device.devices;

import org.amoseman.audiosynth.StereoSignal;
import org.amoseman.audiosynth.TimeData;
import org.amoseman.audiosynth.device.Device;

public class Reverb extends Mixer {
    private final int bufferSize;
    private final int delayCount;
    private final int delayOffset;
    private final StereoSignal[][] delayLines;
    private int position;

    public Reverb(int delayLength, int delayCount, double delayPercentage) {
        this.bufferSize = delayLength;
        this.delayCount = delayCount;
        this.delayOffset = (int) (bufferSize * delayPercentage / delayCount);
        this.delayLines = new StereoSignal[delayCount][bufferSize];
        this.position = 0;
    }

    public static Reverb create(int delayLength, int delayCount, double delayPercentage) {
        return new Reverb(delayLength, delayCount, delayPercentage);
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
        position = (position + 1) % bufferSize;
        StereoSignal out = signal.copy();
        for (int i = 0; i < delayCount; i++) {
            int k = position - 2 - delayOffset;
            if (k < 0) {
                k += bufferSize;
            }
            StereoSignal[] delayLine = delayLines[i];
            delayLine[position] = signal;
            StereoSignal delaySignal = delayLine[k];
            if (delaySignal == null) {
                continue;
            }
            delaySignal = delaySignal.copy().scale(Math.pow(0.5, i + 1));
            out.add(delaySignal);
        }
        return out;
    }
}
