package org.raytracer;

public class Tuple {

    public static float POINT_IDENTIFIER = 1;
    public static float VECTOR_IDENTIFIER = 0;

    public static float[] add(float[] a, float[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Tuples must be of equal length.");
        }

        float[] result = new float[4];

        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] + b[i];
        }

        return result;
    }

    public static boolean areEqual(float[] a, float[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Tuples must be of equal length.");
        }

        for (int i = 0; i < a.length; i++) {
            if (Math.abs(a[i] - b[i]) > Constants.EPSILON)
                return false;
        }

        return true;
    }

    public static float[] cross(float[] a, float[] b) {
        return vector(
                a[1] * b[2] - a[2] * b[1],
                a[2] * b[0] - a[0] * b[2],
                a[0] * b[1] - a[1] * b[0]);
    }

    public static float[] divide(float[] tuple, float scalar) {
        float[] result = new float[4];

        for (int i = 0; i < tuple.length; i++) {
            result[i] = tuple[i] / scalar;
        }

        return result;
    }

    public static float dot(float[] a, float[] b) {
        float product = 0f;

        for (int i = 0; i < a.length; i++) {
            product += a[i] * b[i];
        }

        return product;
    }

    public static boolean isPoint(float[] tuple) {
        return tuple[3] == POINT_IDENTIFIER;
    }

    public static boolean isNormalized(float[] tuple) {
        float magnitude = magnitude(tuple);
        return Math.abs(1f - magnitude) <= Constants.EPSILON;
    }

    public static boolean isVector(float[] tuple) {
        return tuple[3] == VECTOR_IDENTIFIER;
    }

    public static float magnitude(float[] tuple) {
        float sumOfSquares = 0f;

        for (int i = 0; i < tuple.length; i++) {
            sumOfSquares += Math.pow(tuple[i], 2);
        }

        return (float) Math.sqrt(sumOfSquares);
    }

    public static float[] negate(float[] tuple) {
        return scale(tuple, -1f);
    }

    public static float[] normalize(float[] tuple) {
        float magnitude = magnitude(tuple);
        return divide(tuple, magnitude);
    }

    public static float[] point(float... tuple) {
        return appendFloat(tuple, POINT_IDENTIFIER);
    }

    public static String pretty(float[] tuple) {
        String output = "(%f, %f, %f, %f)";
        return String.format(output, tuple[0], tuple[1], tuple[2], tuple[3]);
    }

    public static float[] scale(float[] tuple, float scalar) {
        float[] result = new float[4];

        for (int i = 0; i < tuple.length; i++) {
            result[i] = tuple[i] * scalar;
        }

        return result;
    }

    public static float[] subtract(float[] a, float[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Tuples must be of equal length.");
        }

        float[] result = new float[4];

        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] - b[i];
        }

        return result;
    }

    public static float[] vector(float... tuple) {
        return appendFloat(tuple, VECTOR_IDENTIFIER);
    }

    // PRIVATE

    private static float[] appendFloat(float[] tuple, float value) {
        float[] result = new float[4];
        System.arraycopy(tuple, 0, result, 0, 3);
        result[3] = value;
        return result;
    }

}
