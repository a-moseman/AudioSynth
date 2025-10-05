package org.amoseman.audiosynth.oscillators;

import org.amoseman.stuff.projects.audiosynth.Oscillator;
import org.amoseman.stuff.projects.audiosynth.StereoSignal;

public class Sine implements Oscillator {
    @Override
    public StereoSignal generate(double phase) {
        return StereoSignal.create(Math.sin(phase));
    }
}
