package org.raytracer.patterns;

import org.raytracer.Tuple;

public class Gradient extends Pattern {

    private float[] a, b;

    public Gradient(float[] colorA, float[] colorB) {
        super();
        this.a = colorA;
        this.b = colorB;
    }

    @Override
    public float[] colorAt(float[] point) {
        var localPoint = transform.inverse().multiply(point);
        var distance = Tuple.subtract(b, a);
        var fraction = localPoint[0] - Math.round(localPoint[0]);
        return Tuple.add(a, Tuple.multiply(distance, fraction));
    }

}
