package org.raytracer.patterns;

public class StripePattern implements IPattern {

    float[] colorA, colorB;

    public StripePattern(float[] a, float[] b) {
        colorA = a;
        colorB = b;
    }

    public float[] a() {
        return this.colorA;
    }

    public float[] b() {
        return this.colorB;
    }

    public float[] colorAt(float[] point) {
        return Math.floor(point[0]) % 2 == 0 ? colorA : colorB;
    }

}
