package org.raytracer.patterns;

public class CheckerPattern extends Pattern {

    private Pattern a, b;

    public CheckerPattern(float[] colorA, float[] colorB) {
        this(new SolidPattern(colorA), new SolidPattern(colorB));
    }

    public CheckerPattern(Pattern a, Pattern b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public float[] colorAt(float[] point) {
        var lp = transform.inverse().multiply(point);
        if ((Math.floor(lp[0]) + Math.floor(lp[1]) + Math.floor(lp[2])) % 2 == 0) {
            return a.colorAt(lp);
        } else {
            return b.colorAt(lp);
        }
    }

}
