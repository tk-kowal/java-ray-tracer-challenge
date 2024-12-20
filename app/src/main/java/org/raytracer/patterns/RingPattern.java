package org.raytracer.patterns;

public class RingPattern extends Pattern {

    private float[] a, b;

    public RingPattern(float[] colorA, float[] colorB) {
        this.a = colorA;
        this.b = colorB;
    }

    @Override
    public float[] colorAt(float[] point) {
        var lp = transform.inverse().multiply(point);
        var c = Math.floor(Math.sqrt(Math.pow(lp[0], 2) + Math.pow(lp[2], 2)));
        return c % 2 == 0 ? a : b;
    }

}
