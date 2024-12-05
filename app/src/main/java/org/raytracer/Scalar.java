package org.raytracer;

public class Scalar {
    public static float EPSILON = 0.00001f;

    public static boolean areEqual(float a, float b) {
        return Math.abs(a - b) <= EPSILON;
    }
}
