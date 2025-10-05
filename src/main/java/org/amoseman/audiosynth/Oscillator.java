package org.amoseman.audiosynth;

import org.amoseman.stuff.projects.audiosynth.oscillators.Saw;
import org.amoseman.stuff.projects.audiosynth.oscillators.Sine;
import org.amoseman.stuff.projects.audiosynth.oscillators.Square;

public interface Oscillator {
    StereoSignal generate(double phase);

    Oscillator SINE = new Sine();
    Oscillator SQUARE = new Square();
    Oscillator SAW = new Saw();
}
