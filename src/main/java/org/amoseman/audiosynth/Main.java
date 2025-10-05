package org.amoseman.audiosynth;

import org.amoseman.audiosynth.device.devices.*;

import javax.swing.*;

public class Main {
    public static void main(String... args) {
        AudioEngine e = new AudioEngine();
        e.setBeatsPerMinute(65);

        Sequencer sequencer = Sequencer.create()
                .connectDevice(1.0,
                        Reverb.create().connectDevice(1.0,
                        Reverb.create().connectDevice(1.0,
                        Reverb.create().connectDevice(1.0,
                        Reverb.create().connectDevice(1.0,
                                LowPassFilter.create(1000).connectDevice(1.0,
                                        AdditiveSynth.create()
                                                .addOscillator(Oscillator.SAW, 2.01, 0.1, 0.1, 1.0)
                                                .addOscillator(Oscillator.SAW, 1.99, 0.1, -0.1, 1.0)
                                                .addOscillator(Oscillator.SAW, 1.0, 0.1, 0.0, 1.0)
                                                .addOscillator(Oscillator.SQUARE,  0.5, 0.1, 0.0, 0.0)
                                                .addOscillator(Oscillator.SINE, 1.0, 0.1, 0.0, 0.0)
                                                .addOscillator(Oscillator.SINE, 0.25, 0.1, 0.0, 0.0)))))))
                .addNotes(e.note(30, 1.0))
                .addNotes(e.note(34, 1.0))
                .addNotes(e.note(37, 1.0))
                .addNotes(e.note(38, 1.0));

        JFrame frame = new JFrame();

        Visualizer visualizer = new Visualizer(frame, e.getBufferLength() / 2);
        e.setAudioProcessorListener(visualizer);

        frame.add(visualizer);
        frame.setVisible(true);

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException exception) {
            throw new RuntimeException(exception);
        }

        while (true) {
            e.step(sequencer.process(e.timeData()));
        }
    }
}
