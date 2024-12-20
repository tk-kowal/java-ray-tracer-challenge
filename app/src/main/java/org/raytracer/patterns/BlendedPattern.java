package org.raytracer.patterns;

import org.raytracer.Tuple;

public class BlendedPattern extends Pattern {

    private Pattern a, b;

    public BlendedPattern(Pattern a, Pattern b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public float[] colorAt(float[] point) {
        var lp = transform.inverse().multiply(point);
        return Tuple.add(a.colorAt(lp), b.colorAt(lp));
    }

}
