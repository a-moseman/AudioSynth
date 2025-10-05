package org.amoseman.audiosynth;

public class Note {
    public static final double A4 = 440.0;

    public static final double C0 = 16.35;
    public static final double CS0 = 17.32;
    public static final double D0 = 18.35;
    public static final double DS0 = 19.45;
    public static final double E0 = 20.6;
    public static final double F0 = 21.83;
    public static final double FS0 = 23.12;
    public static final double G0 = 24.5;
    public static final double GS0 = 25.96;
    public static final double A0 = 27.5;
    public static final double AS0 = 29.14;
    public static final double B0 = 30.87;

    private static final double[] NOTE_ARRAY = new double[]{C0, CS0, D0, DS0, E0, F0, FS0, G0, GS0, A0, AS0, B0};

    public static double get(double note, int octave) {
        if (octave < 0) {
            throw new IllegalArgumentException("Note octaves can not be negative.");
        }
        return note * Math.pow(2, octave);
    }

    public static double get(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Can not get note at negative number.");
        }
        int n = index % NOTE_ARRAY.length;
        int o = index / NOTE_ARRAY.length;
        return get(NOTE_ARRAY[n], o);
    }
}
