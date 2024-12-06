package org.raytracer;

public class Scalar {
    public static boolean areEqual(float a, float b) {
        return Math.abs(a - b) <= Constants.EPSILON;
    }
}
