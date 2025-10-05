package org.amoseman.audiosynth.device;

import org.amoseman.stuff.projects.audiosynth.NoteData;
import org.amoseman.stuff.projects.audiosynth.StereoSignal;
import org.amoseman.stuff.projects.audiosynth.TimeData;

import java.util.ArrayList;
import java.util.List;

public abstract class Device {
    protected final List<NoteData> notes;
    protected final List<Device> inputDevices;
    protected final List<Double> inputAmplitudes;

    public Device() {
        this.inputDevices = new ArrayList<>();
        this.inputAmplitudes = new ArrayList<>();
        this.notes = new ArrayList<>();
    }

    /**
     * Set the note data for the next played sound.
     * @param notes the note data.
     * @return this synth.
     */
    public Device setNotes(NoteData... notes) {
        return setNotes(List.of(notes));
    }

    /**
     * Set the note data for the next played sound.
     * @param notes the note data.
     * @return this synth.
     */
    public Device setNotes(List<NoteData> notes) {
        this.notes.clear();
        this.notes.addAll(notes);
        return this;
    }

    public Device propogateNotes(NoteData... notes) {
        inputDevices.forEach(device -> device.setNotes(notes));
        return this;
    }

    public Device propogateNotes(List<NoteData> notes) {
        inputDevices.forEach(device -> device.setNotes(notes));
        return this;
    }

    public Device connectDevice(double amplitude, Device device) {
        inputDevices.add(device);
        inputAmplitudes.add(amplitude);
        return this;
    }


    public abstract StereoSignal process(TimeData timeData);
}
