package org.amoseman.audiosynth;

public interface AudioProcessorListener {
    void onFull(byte[] leftSampleBuffer, byte[] rightSampleBuffer);
}
