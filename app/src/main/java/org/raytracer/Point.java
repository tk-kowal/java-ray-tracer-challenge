package org.raytracer;

public class Point extends Tuple {

    public static float POINT_IDENTIFIER = 1;

    public static float[] point(float... tuple) {
        return appendFloat(tuple, POINT_IDENTIFIER);
    }

    public static boolean isPoint(float[] tuple) {
        return tuple[3] == POINT_IDENTIFIER;
    }

}
