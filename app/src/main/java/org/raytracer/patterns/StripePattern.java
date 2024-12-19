package org.raytracer.patterns;

import org.raytracer.Matrix;

public class StripePattern extends Pattern {

    float[] colorA, colorB;

    public StripePattern(float[] a, float[] b) {
        super();
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
        var localPoint = transform.inverse().multiply(point);
        return Math.floor(localPoint[0]) % 2 == 0 ? colorA : colorB;
    }
}
