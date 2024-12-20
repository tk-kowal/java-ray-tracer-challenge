package org.raytracer.patterns;

public class SolidPattern extends Pattern {

    private float[] color;

    public SolidPattern(float[] color) {
        this.color = color;
    }

    @Override
    public float[] colorAt(float[] point) {
        return color;
    }

}
