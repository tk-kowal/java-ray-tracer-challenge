package org.raytracer;

public class Color extends Tuple {

    private static int RED_INDEX = 0;
    private static int BLUE_INDEX = 1;
    private static int GREEN_INDEX = 2;

    public static float[] RED = color(1, 0, 0);
    public static float[] GREEN = color(0, 1, 0);
    public static float[] BLUE = color(0, 0, 1);
    public static float[] WHITE = color(1, 1, 1);
    public static float[] BLACK = color(0, 0, 0);

    public static float[] color(float... components) {
        return components;
    }

    public static float red(float[] components) {
        return components[RED_INDEX];
    }

    public static float green(float[] components) {
        return components[BLUE_INDEX];
    }

    public static float blue(float[] components) {
        return components[GREEN_INDEX];
    }

    public static float[] multiply(float[] colorA, float[] colorB) {
        float[] newColor = new float[3];
        for (int i = 0; i < colorA.length; i++) {
            newColor[i] = colorA[i] * colorB[i];
        }
        return newColor;
    }

}
