package org.raytracer.patterns;

public class CheckerPattern extends Pattern {

    private float[] a, b;

    public CheckerPattern(float[] colorA, float[] colorB) {
        this.a = colorA;
        this.b = colorB;
    }

    @Override
    public float[] colorAt(float[] point) {
        var lp = transform.inverse().multiply(point);
        if ((Math.floor(lp[0]) + Math.floor(lp[1]) + Math.floor(lp[2])) % 2 == 0) {
            return a;
        } else {
            return b;
        }
    }

}
