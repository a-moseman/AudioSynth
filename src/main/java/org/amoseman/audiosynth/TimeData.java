package org.amoseman.audiosynth;

public class TimeData {
    public final double time;
    public final int beat;
    public final double deltaTime;

    private TimeData(double time, int beat, double deltaTime) {
        this.time = time;
        this.beat = beat;
        this.deltaTime = deltaTime;
    }

    public static TimeData create(double time, int beat, double deltaTime) {
        return new TimeData(time, beat, deltaTime);
    }
}
