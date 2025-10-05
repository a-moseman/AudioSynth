package org.amoseman.audiosynth;

public class StereoSignal {
    public double leftSignal;
    public double rightSignal;

    public StereoSignal(double leftSignal, double rightSignal) {
        this.leftSignal = leftSignal;
        this.rightSignal = rightSignal;
    }

    public static StereoSignal create(double leftSignal, double rightSignal) {
        return new StereoSignal(leftSignal, rightSignal);
    }

    public static StereoSignal create(double monoSignal) {
        return new StereoSignal(monoSignal, monoSignal);
    }

    public static StereoSignal zero() {
        return new StereoSignal(0, 0);
    }

    public StereoSignal copy() {
        return StereoSignal.create(leftSignal, rightSignal);
    }

    public StereoSignal add(StereoSignal other) {
        leftSignal += other.leftSignal;
        rightSignal += other.rightSignal;
        return this;
    }

    public StereoSignal scale(double scalar) {
        leftSignal *= scalar;
        rightSignal *= scalar;
        return this;
    }

    public StereoSignal mono() {
        double mean = (leftSignal + rightSignal) / 2;
        leftSignal = mean;
        rightSignal = mean;
        return this;
    }

    public StereoSignal pan(double panning) {
        double r = (panning + 1.0) * 0.5; // normalize into [0, 1] range
        double l = (1.0 - r);
        leftSignal *= l;
        rightSignal *= r;
        return this;
    }

    public double total() {
        return leftSignal + rightSignal;
    }
}
