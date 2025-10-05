package org.amoseman.audiosynth;

public class AudioEngine {
    private final ProcessingFormat format = new ProcessingFormat(48000, 96 * 2, 2);//96, 2);
    private final AudioProcessor processor = new AudioProcessor(format);

    private double beatsPerMinute;
    private double beatsPerSecond;
    private double secondsPerBeat;

    private double time = 0.0;
    private int beat = 0;

    public NoteData note(int index, double amplitude) {
        return NoteData.create(Note.get(index), amplitude);
    }

    public TimeData timeData() {
        return TimeData.create(time, beat, format.deltaTime / format.buffersPerSecond);
    }

    public void setBeatsPerMinute(double bpm) {
        beatsPerMinute = bpm;
        beatsPerSecond = beatsPerMinute / 60;
        secondsPerBeat = 1.0 / beatsPerSecond;
    }

    public void setAudioProcessorListener(AudioProcessorListener listener) {
        processor.setListener(listener);
    }

    /**
     * Each invocation of step represents 1 beat.
     */
    public void step(StereoSignal signal) {
        processor.process(signal);
        time += format.deltaTime / format.buffersPerSecond;
        beat = (int) (time / secondsPerBeat);
    }

    public int getBufferLength() {
        return processor.getBufferLength();
    }
}
