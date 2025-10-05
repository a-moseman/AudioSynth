package org.amoseman.audiosynth.device.devices;

import org.amoseman.audiosynth.NoteData;
import org.amoseman.audiosynth.Oscillator;
import org.amoseman.audiosynth.StereoSignal;
import org.amoseman.audiosynth.TimeData;
import org.amoseman.audiosynth.device.Device;

import java.util.ArrayList;
import java.util.List;

public class AdditiveSynth extends Mixer {
    private final List<Oscillator> oscillators;
    private final List<Double> pitches;
    private final List<Double> amplitudes;
    private final List<Double> pannings;
    private final List<Double> phaseRandomization;

    private AdditiveSynth() {
        this.oscillators = new ArrayList<>();
        this.pitches = new ArrayList<>();
        this.amplitudes = new ArrayList<>();
        this.pannings = new ArrayList<>();
        this.phaseRandomization = new ArrayList<>();
    }

    public static AdditiveSynth create() {
        return new AdditiveSynth();
    }

    public AdditiveSynth addOscillator(Oscillator oscillator, double pitch, double amplitude, double panning, double randomization) {
        oscillators.add(oscillator);
        pitches.add(pitch);
        amplitudes.add(amplitude);
        pannings.add(panning);
        phaseRandomization.add(randomization * Math.random() * Math.PI * 2);
        return this;
    }

    @Override
    public Device connectDevice(double amplitude, Device device) {
        throw new RuntimeException("Can not connect device to AdditiveSynth as it should be a leaf node on the tree.");
    }

    @Override
    public StereoSignal process(TimeData timeData) {
        StereoSignal signal = StereoSignal.zero();
        for (NoteData note : notes) {
            for (int i = 0; i < oscillators.size(); i++) {
                Oscillator osc = oscillators.get(i);
                double freq = note.freq * pitches.get(i);
                double amp = note.amp * amplitudes.get(i);
                double pan = pannings.get(i);
                double offset = phaseRandomization.get(i);
                StereoSignal s = osc.generate(2.0 * Math.PI * (timeData.time + offset) * freq).scale(amp).pan(pan);
                signal.add(s);
            }
        }
        return signal;
    }
}
