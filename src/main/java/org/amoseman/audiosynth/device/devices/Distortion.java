package org.amoseman.audiosynth.device.devices;

import org.amoseman.stuff.projects.audiosynth.StereoSignal;
import org.amoseman.stuff.projects.audiosynth.TimeData;
import org.amoseman.stuff.projects.audiosynth.device.Device;
import org.amoseman.stuff.tools.Functions;

public class Distortion extends Mixer {
    private final double amplitude;
    private final double beta;

    public Distortion(double amplitude, double beta) {
        this.amplitude = amplitude;
        this.beta = beta;
    }

    public static Distortion create(double amplitude, double beta) {
        return new Distortion(amplitude, beta);
    }

    @Override
    public Distortion connectDevice(double amplitude, Device device) {
        return (Distortion) super.connectDevice(amplitude, device);
    }

    @Override
    public StereoSignal process(TimeData timeData) {
        propogateNotes(notes);
        StereoSignal signal = sumDeviceSignals(timeData);
        signal.scale(amplitude);
        signal.leftSignal = Functions.sigmoid(signal.leftSignal, beta) * 2 - 1;
        signal.rightSignal = Functions.sigmoid(signal.rightSignal, beta) * 2 - 1;
        return signal;
    }
}
