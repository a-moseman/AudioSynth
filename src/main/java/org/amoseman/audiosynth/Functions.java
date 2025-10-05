package org.amoseman.audiosynth;

public class Functions {
    public static double sigmoid(double x, double beta) {
        return 1.0 / (1.0 + Math.pow(Math.E, -x * beta));
    }
}
