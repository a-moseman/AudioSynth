package org.amoseman.audiosynth.oscillators;

import org.amoseman.audiosynth.Oscillator;
import org.amoseman.audiosynth.StereoSignal;

public class Sine implements Oscillator {
    @Override
    public StereoSignal generate(double phase) {
        return StereoSignal.create(Math.sin(phase));
    }
}
