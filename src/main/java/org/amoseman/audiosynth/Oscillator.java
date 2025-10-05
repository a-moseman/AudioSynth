package org.amoseman.audiosynth;

import org.amoseman.audiosynth.oscillators.Saw;
import org.amoseman.audiosynth.oscillators.Sine;
import org.amoseman.audiosynth.oscillators.Square;

public interface Oscillator {
    StereoSignal generate(double phase);

    Oscillator SINE = new Sine();
    Oscillator SQUARE = new Square();
    Oscillator SAW = new Saw();
}
