package org.raytracer.patterns;

public class StripePattern extends Pattern {

    Pattern a, b;

    public StripePattern(float[] a, float[] b) {
        this(new SolidPattern(a), new SolidPattern(b));
    }

    public StripePattern(Pattern a, Pattern b) {
        this.a = a;
        this.b = b;
    }

    public float[] colorAt(float[] point) {
        var lp = transform.inverse().multiply(point);
        return Math.floor(lp[0]) % 2 == 0 ? a.colorAt(lp) : b.colorAt(lp);
    }
}
