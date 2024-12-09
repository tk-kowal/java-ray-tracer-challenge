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

    public static boolean isNormalized(float[] tuple) {
        float magnitude = magnitude(tuple);
        return Math.abs(1f - magnitude) <= Constants.EPSILON;
    }

    public static float magnitude(float[] vector) {
        float sumOfSquares = 0f;

        for (int i = 0; i < vector.length; i++) {
            sumOfSquares += Math.pow(vector[i], 2);
        }

        return (float) Math.sqrt(sumOfSquares);
    }

    public static float[] normalize(float[] tuple) {
        float magnitude = magnitude(tuple);
        return divide(tuple, magnitude);
    }

    public static float[] reflect(float[] vector, float[] normal) {
        return subtract(vector, multiply(multiply(normal, 2), dot(vector, normal)));
    }
}
