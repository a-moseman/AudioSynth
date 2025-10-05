package org.amoseman.audiosynth.oscillators;

import org.amoseman.stuff.projects.audiosynth.Oscillator;
import org.amoseman.stuff.projects.audiosynth.StereoSignal;

public class Saw implements Oscillator {
    @Override
    public StereoSignal generate(double phase) {
        double p = phase / Math.PI * 0.5;
        return StereoSignal.create((p - Math.floor(p)) * 2.0 - 1.0);
    }
}
