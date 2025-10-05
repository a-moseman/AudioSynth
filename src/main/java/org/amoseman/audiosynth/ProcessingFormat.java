package org.amoseman.audiosynth;

public class ProcessingFormat {
    private final int sampleFrames;

    public final int bytesPerSample;
    public final int sampleRate;
    public final int buffersPerSecond;
    public final double deltaTime;
    public final int bitResolution;
    public final int channels;
    public final int bufferSize;

    /**
     * The audio processing format to use.
     * @param sampleRate the number of samples per second.
     * @param buffersPerSecond the number of buffers per second.
     * @param channels the number of audio channels.
     */
    public ProcessingFormat(int sampleRate, int buffersPerSecond, int channels) {
        this.bitResolution = 16;
        this.bytesPerSample = bitResolution / Byte.SIZE;
        this.sampleFrames = bytesPerSample * channels;
        this.sampleRate = sampleRate;
        this.buffersPerSecond = buffersPerSecond;
        this.deltaTime = (double) buffersPerSecond / sampleRate;
        this.channels = channels;
        this.bufferSize = calculateBufferSize();
    }

    private int calculateBufferSize() {
        double samplesPerBuffer = (double) sampleRate / buffersPerSecond;
        double bytesPerBuffer = samplesPerBuffer * bytesPerSample;
        if ((int) bytesPerBuffer % sampleFrames != 0) {
            throw new IllegalArgumentException("Buffer length must be divisible by sample frames.");
        }
        return (int) bytesPerBuffer;
    }
}
