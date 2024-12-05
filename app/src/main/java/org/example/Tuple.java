package org.example;

public class Tuple {

    static float POINT_IDENTIFIER = 1;
    static float VECTOR_IDENTIFIER = 0;

    public static boolean isPoint(float[] tuple) {
        return tuple[3] == POINT_IDENTIFIER;
    }

    public static boolean isVector(float[] tuple) {
        return tuple[3] == VECTOR_IDENTIFIER;
    }

    public static float[] point(float... tuple) {
        return appendFloat(tuple, POINT_IDENTIFIER);
    }

    public static float[] vector(float... tuple) {
        return appendFloat(tuple, VECTOR_IDENTIFIER);
    }

    private static float[] appendFloat(float[] tuple, float value) {
        float[] result = new float[4];
        System.arraycopy(tuple, 0, result, 0, 3);
        result[3] = value;
        return result;
    }

}
