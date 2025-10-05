package org.amoseman.audiosynth;

public class NoteData {
    public final double freq;
    public final double amp;

    private NoteData(double freq, double amp) {
        this.freq = freq;
        this.amp = amp;
    }

    public static NoteData create(double freq, double amp) {
        return new NoteData(freq, amp);
    }
}
