package org.amoseman.audiosynth;

import javax.sound.sampled.*;

public class AudioProcessor {
    private final SampleBuffer leftSampleBuffer;
    private final SampleBuffer rightSampleBuffer;
    private SourceDataLine leftDataLine;
    private SourceDataLine rightDataLine;
    private AudioProcessorListener listener;

    public void setListener(AudioProcessorListener listener) {
        this.listener = listener;
    }

    public AudioProcessor(ProcessingFormat processingFormat) {
        this.leftSampleBuffer = new SampleBuffer(processingFormat.bufferSize);
        this.rightSampleBuffer = new SampleBuffer(processingFormat.bufferSize);
        try {
            AudioFormat format = new AudioFormat(
                    processingFormat.sampleRate,
                    processingFormat.bitResolution,
                    processingFormat.channels,
                    true,
                    true);
            this.leftDataLine = AudioSystem.getSourceDataLine(format);
            this.leftDataLine.open(format);
            this.leftDataLine.start();
            ((FloatControl) leftDataLine.getControl(FloatControl.Type.BALANCE)).setValue(-1.0f);
            ((FloatControl) leftDataLine.getControl(FloatControl.Type.MASTER_GAIN)).setValue(0.33f);
            this.rightDataLine = AudioSystem.getSourceDataLine(format);
            this.rightDataLine.open(format);
            this.rightDataLine.start();
            ((FloatControl) rightDataLine.getControl(FloatControl.Type.BALANCE)).setValue(1.0f);
            ((FloatControl) rightDataLine.getControl(FloatControl.Type.MASTER_GAIN)).setValue(0.33f);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void process(StereoSignal signal) {
        leftSampleBuffer.add(signal.leftSignal);
        rightSampleBuffer.add(signal.rightSignal);
        if (!leftSampleBuffer.isFull() || !rightSampleBuffer.isFull()) {
            return;
        }
        byte[] leftBuffer = leftSampleBuffer.read();
        byte[] rightBuffer = rightSampleBuffer.read();

        listener.onFull(leftBuffer, rightBuffer);

        leftDataLine.write(leftBuffer, 0, leftBuffer.length);
        rightDataLine.write(rightBuffer, 0, rightBuffer.length);
    }

    public int getBufferLength() {
        return leftSampleBuffer.length();
    }
}
