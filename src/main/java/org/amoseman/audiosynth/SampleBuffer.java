package org.amoseman.audiosynth;

import java.nio.ByteBuffer;

public class SampleBuffer {
    private final int size;
    private ByteBuffer bb;

    public SampleBuffer(int bufferLength) {
        this.size = bufferLength;
        this.bb = ByteBuffer.allocate(size);
    }

    public void add(double sample) {
        short sample16Bit = (short) Math.max(Math.min(Short.MAX_VALUE, sample * Short.MAX_VALUE), Short.MIN_VALUE);
        //buffer[position] = (byte) ((sample16Bit >> 8) & 0xff);
        //buffer[position + 1] = (byte) (sample16Bit & 0xff);
        //bb.putShort(position, sample16Bit);
        bb.putShort(sample16Bit);
        //position += 2;
    }

    public byte[] read() {
        bb.position(0);
        return bb.array();
    }

    public boolean isFull() {
        return bb.position() == size;
    }

    public int length() {
        return size;
    }
}
