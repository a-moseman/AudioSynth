package org.amoseman.audiosynth.device.devices;

import org.amoseman.audiosynth.NoteData;
import org.amoseman.audiosynth.StereoSignal;
import org.amoseman.audiosynth.TimeData;
import org.amoseman.audiosynth.device.Device;

import java.util.ArrayList;
import java.util.List;

public class Sequencer extends Mixer {
    private final List<NoteData[]> noteSequence;

    private Sequencer() {
        this.noteSequence = new ArrayList<>();
    }

    public static Sequencer create() {
        return new Sequencer();
    }

    public Sequencer addNotes(NoteData... notes) {
        noteSequence.add(notes);
        return this;
    }


    @Override
    public Sequencer connectDevice(double amplitude, Device device) {
        super.connectDevice(amplitude, device);
        return this;
    }

    @Override
    public Sequencer setNotes(NoteData... notes) {
        super.setNotes(notes);
        return this;
    }

    @Override
    public StereoSignal process(TimeData timeData) {
        int b = timeData.beat % noteSequence.size();
        NoteData[] notes = noteSequence.get(b);
        propogateNotes(notes);
        return sumDeviceSignals(timeData);
    }
}
