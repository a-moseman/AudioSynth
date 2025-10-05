package org.amoseman.audiosynth.oscillators;

import org.amoseman.stuff.projects.audiosynth.Oscillator;
import org.amoseman.stuff.projects.audiosynth.StereoSignal;

public class Square implements Oscillator {
    @Override
    public StereoSignal generate(double phase) {
        //return phase - Math.floor(phase);
        return StereoSignal.create(Math.signum(Math.sin(phase)));
    }
}
