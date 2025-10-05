package org.amoseman.audiosynth;

import org.amoseman.stuff.projects.audiosynth.device.devices.AdditiveSynth;
import org.amoseman.stuff.projects.audiosynth.device.devices.Distortion;
import org.amoseman.stuff.projects.audiosynth.device.devices.Reverb;
import org.amoseman.stuff.projects.audiosynth.device.devices.Sequencer;

import javax.swing.*;

public class Main {
    public static void main(String... args) {
        AudioEngine e = new AudioEngine();
        e.setBeatsPerMinute(65);


        AdditiveSynth synth = AdditiveSynth.create();
                //.addOscillator(Oscillator.SINE, 0.25, 0.5, 0.0);
        int n = 16;
        for (int i = 0; i < n; i++) {
            synth.addOscillator(
                    Oscillator.SAW,
                    0.25 + Math.random() * 0.01 - 0.005,
                    1.0 / n,
                    Math.random() - 0.5);
        }
        synth.addOscillator(Oscillator.SINE, 0.25, 0.33, 0.0);

        Distortion distortion = Distortion.create(1.0, 2.0).connectDevice(1.0, synth);
        Reverb reverb = Reverb.create().connectDevice(1.0, distortion);

        Sequencer sequencer = Sequencer.create()
                //.connectDevice(0.1, reverb)
                .connectDevice(0.1,
                        Reverb.create().connectDevice(1.0,
                        Reverb.create().connectDevice(1.0,
                        Reverb.create().connectDevice(1.0,
                        Reverb.create().connectDevice(1.0,
                                Distortion.create(1.0, 1.0).connectDevice(1.0,
                                        AdditiveSynth.create()
                                                .addOscillator(Oscillator.SAW, 2.01, 0.2, 0.1)
                                                .addOscillator(Oscillator.SAW, 1.99, 0.2, -0.1)
                                                .addOscillator(Oscillator.SAW, 1.0, 0.1, 0.0)
                                                .addOscillator(Oscillator.SQUARE,  0.5, 0.1, 0.0)
                                                .addOscillator(Oscillator.SINE, 1.0, 0.3, 0.0)
                                                .addOscillator(Oscillator.SINE, 0.25, 0.5, 0.0)))))))
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
