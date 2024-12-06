package org.raytracer;

public class Vector extends Tuple {
    public static float VECTOR_IDENTIFIER = 0;

    public static float[] vector(float... tuple) {
        return appendFloat(tuple, VECTOR_IDENTIFIER);
    }

    public static boolean isVector(float[] tuple) {
        return tuple[3] == VECTOR_IDENTIFIER;
    }

    public static float[] cross(float[] a, float[] b) {
        return vector(
                a[1] * b[2] - a[2] * b[1],
                a[2] * b[0] - a[0] * b[2],
                a[0] * b[1] - a[1] * b[0]);
    }
}
