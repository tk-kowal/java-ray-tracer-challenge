package org.raytracer;

public class Color extends Tuple {

    private static int RED = 0;
    private static int BLUE = 1;
    private static int GREEN = 2;

    public static float[] color(float... components) {
        return components;
    }

    public static float red(float[] components) {
        return components[RED];
    }

    public static float green(float[] components) {
        return components[BLUE];
    }

    public static float blue(float[] components) {
        return components[GREEN];
    }

    public static float[] multiply(float[] colorA, float[] colorB) {
        float[] newColor = new float[3];
        for (int i = 0; i < colorA.length; i++) {
            newColor[i] = colorA[i] * colorB[i];
        }
        return newColor;
    }

}
